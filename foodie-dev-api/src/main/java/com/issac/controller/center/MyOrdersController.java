package com.issac.controller.center;

import com.issac.controller.BaseController;
import com.issac.pojo.Orders;
import com.issac.pojo.vo.OrderStatusCountsVO;
import com.issac.service.center.MyOrdersService;
import com.issac.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-05-31
 * @desc:
 */
@RestController
@RequestMapping("/myorders")
@Api(value = "用户中心订单接口", tags = "用户中心订单接口")
public class MyOrdersController extends BaseController {

    @PostMapping("/query")
    @ApiOperation(value = "查询用户订单", notes = "查询用户订单", httpMethod = "POST")
    public JSONResult query(@RequestParam String userId,
                            @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
                            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("null");
        }
        return JSONResult.ok(myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize));
    }

    @PostMapping("/confirmReceive")
    @ApiOperation(value = "确认收货", notes = "确认收货", httpMethod = "POST")
    public JSONResult confirmReceive(@RequestParam String userId,
                                     @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("null");
        }

        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        boolean result = myOrdersService.updateReceiveOrderStatus(orderId);
        return JSONResult.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
    public JSONResult delete(@RequestParam String userId,
                             @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("null");
        }
        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }

        boolean result = myOrdersService.deleteOrder(userId, orderId);
        return JSONResult.ok();
    }

    @GetMapping("/deliver")
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    public JSONResult query(@RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return JSONResult.errorMsg("null");
        }
        myOrdersService.updateDeliverOrderStatus(orderId);
        return JSONResult.ok();
    }

    @PostMapping("/statusCounts")
    @ApiOperation(value = "订单各状态数量", notes = "订单各状态数量", httpMethod = "POST")
    public JSONResult statusCounts(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("null");
        }
        OrderStatusCountsVO orderStatusCounts = myOrdersService.getOrderStatusCounts(userId);
        return JSONResult.ok(orderStatusCounts);
    }

    @PostMapping("/trend")
    @ApiOperation(value = "查询用户订单", notes = "查询用户订单", httpMethod = "POST")
    public JSONResult trend(@RequestParam String userId,
                            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("null");
        }
        return JSONResult.ok(myOrdersService.getOrderTrend(userId, page, pageSize));
    }
}
