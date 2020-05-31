package com.issac.controller;

import com.issac.enums.OrderStatusEnum;
import com.issac.enums.PayMethod;
import com.issac.pojo.OrderStatus;
import com.issac.pojo.Orders;
import com.issac.pojo.UserAddress;
import com.issac.pojo.bo.AddressBO;
import com.issac.pojo.bo.SubmitOrderBO;
import com.issac.pojo.vo.MerchantOrdersVO;
import com.issac.pojo.vo.OrderVO;
import com.issac.service.AddressService;
import com.issac.service.OrderService;
import com.issac.util.CookieUtils;
import com.issac.util.JSONResult;
import com.issac.util.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/create")
    @ApiOperation(value = "用户订单", notes = "用户订单", httpMethod = "POST")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        System.out.println(submitOrderBO);
        if (!submitOrderBO.getPayMethod().equals(PayMethod.ALIPAY.type) &&
                !submitOrderBO.getPayMethod().equals(PayMethod.WEIXIN.type)) {
            return JSONResult.errorMsg("支付方式不支持");
        }

        // 1. 创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);

        // 2. 创建订单以后，移除购物车中已结算（已提交）的商品
        // TODO 整合 redis 完善购物车的已结算商品清除，并且同步前端的cookie
//        CookieUtils.deleteCookie(request, response, FOODIE_SHOPCART);

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
