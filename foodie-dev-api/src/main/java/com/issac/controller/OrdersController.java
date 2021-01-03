package com.issac.controller;

import com.issac.enums.OrderStatusEnum;
import com.issac.enums.PayMethod;
import com.issac.pojo.OrderStatus;
import com.issac.pojo.Orders;
import com.issac.pojo.UserAddress;
import com.issac.pojo.bo.AddressBO;
import com.issac.pojo.bo.ShopCartItemBO;
import com.issac.pojo.bo.SubmitOrderBO;
import com.issac.pojo.vo.MerchantOrdersVO;
import com.issac.pojo.vo.OrderVO;
import com.issac.service.AddressService;
import com.issac.service.OrderService;
import com.issac.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@RestController
@RequestMapping("orders")
@Api(value = "订单相关", tags = {"订单相关"})
public class OrdersController extends BaseController {

    @Resource
    OrderService orderService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RedisOperator redisOperator;

    @Autowired
    private RedissonClient redissonClient;

    @ApiOperation(value = "获取订单token", notes = "获取订单token", httpMethod = "POST")
    @PostMapping("/getOrderToken")
    public JSONResult getOrderToken(HttpSession session) {
        String token = UUID.randomUUID().toString();
        redisOperator.set("ORDER_TOKEN_" + session.getId(), token, 600);
        return JSONResult.ok(token);
    }

    @PostMapping("/create")
    @ApiOperation(value = "用户订单", notes = "用户订单", httpMethod = "POST")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        String orderTokenKey = "ORDER_TOKEN_" + request.getSession().getId();
        String lockKey = "LOCK_KEY_" + request.getSession().getId();
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(5, TimeUnit.SECONDS);
        try {
            String token = redisOperator.get(orderTokenKey);
            if (StringUtils.isBlank(token)) {
                throw new RuntimeException("orderToken不存在");
            }
            if (!token.equals(submitOrderBO.getToken())) {
                throw new RuntimeException("orderToken不正确");
            }
            redisOperator.del(orderTokenKey);
        } finally {
            try {
                lock.unlock();
            } catch (Exception e) {
            }
        }
        System.out.println(submitOrderBO);
        if (!submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type) &&
                !submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)) {
            return JSONResult.errorMsg("支付方式不支持");
        }
        String shopcartJson = redisOperator.get(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId());
        if (StringUtils.isBlank(shopcartJson)) {
            return JSONResult.errorMsg("购物车不能为空！");
        }

        // 1. 创建订单
        List<ShopCartItemBO> shopCartList = JsonUtils.jsonToList(shopcartJson, ShopCartItemBO.class);

        OrderVO orderVO = orderService.createOrder(shopCartList, submitOrderBO);

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        // 清理覆盖现有redis购物车数据
        shopCartList.removeAll(orderVO.getToBeRemovedShopcartList());
        redisOperator.set(FOODIE_SHOPCART + ":" + submitOrderBO.getUserId(), JsonUtils.objectToJson(shopCartList));
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, JsonUtils.objectToJson(shopCartList), true);

        // 3. 向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);
        HttpHeaders headers = new HttpHeaders();
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, headers);

        ResponseEntity<JSONResult> responseEntity = restTemplate.postForEntity(PAYMENT_URL, entity, JSONResult.class);
        JSONResult paymentResult = responseEntity.getBody();
        if (!paymentResult.isOK()) {
            return JSONResult.errorMsg("支付中心订单创建失败，请联系管理员！");
        }
        return JSONResult.ok(orderVO.getOrderId());
    }

    @RequestMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        Orders orders = orderService.queryOrdersByOrderId(merchantOrderId);
        // 3. 模拟异步调用支付中心
        HttpHeaders headers = new HttpHeaders();
        headers.add("imoocUserId", "imooc");
        headers.add("password", "imooc");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);
        String url = PAYMENT_ASYNC_RETURN_URL + "?orderId=" + merchantOrderId + "&paidAmount=" + (orders.getRealPayAmount() + orders.getPostAmount());
        ResponseEntity<JSONResult> responseEntity = restTemplate.postForEntity(url, entity, JSONResult.class);

        return HttpStatus.OK.value();
    }

    @PostMapping("getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }
}
