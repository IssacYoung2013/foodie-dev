package com.issac.controller.center;

import com.issac.controller.BaseController;
import com.issac.enums.YesOrNo;
import com.issac.pojo.OrderItems;
import com.issac.pojo.Orders;
import com.issac.pojo.Users;
import com.issac.pojo.bo.center.OrderItemsCommentBO;
import com.issac.service.center.CenterUserService;
import com.issac.service.center.MyCommentService;
import com.issac.service.center.MyOrdersService;
import com.issac.util.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-31
 * @desc:
 */
@RestController
@RequestMapping("/mycomments")
@Api(value = "用户中心评价模块", tags = "用户中心评价模块的相关接口")
public class MyCommentController extends BaseController {

    @Resource
    MyCommentService myCommentService;

    @ApiOperation(value = "获取用户评价", notes = "获取用户评价", httpMethod = "POST")
    @PostMapping("pending")
    public JSONResult pending(@RequestParam @ApiParam(name = "userId",
            value = "用户id", required = true) String userId, @RequestParam @ApiParam(name = "orderId",
            value = "订单id", required = true) String orderId) {
        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        Orders myOrder = (Orders) jsonResult.getData();
        if (myOrder.getIsComment().equals(YesOrNo.YES.type)) {
            return JSONResult.errorMsg("该笔订单已评价");
        }
        List<OrderItems> orderItems = myCommentService.queryPendingComment(orderId);
        return JSONResult.ok(orderItems);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("saveList")
    public JSONResult saveList(@RequestParam @ApiParam(name = "userId",
            value = "用户id", required = true) String userId, @RequestParam @ApiParam(name = "orderId",
            value = "订单id", required = true) String orderId,
                               @RequestBody List<OrderItemsCommentBO> commentList) {
        System.out.println(commentList);
        JSONResult jsonResult = checkUserOrder(userId, orderId);
        if (jsonResult.getStatus() != HttpStatus.OK.value()) {
            return jsonResult;
        }
        if (CollectionUtils.isEmpty(commentList)) {
            return JSONResult.errorMsg("评论内容不能为空！");
        }
        myCommentService.saveComments(orderId, userId, commentList);
        return JSONResult.ok();
    }

    @PostMapping("/query")
    @ApiOperation(value = "查询用户评论", notes = "查询用户评论", httpMethod = "POST")
    public JSONResult query(@RequestParam String userId,
                            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("null");
        }
        return JSONResult.ok(myCommentService.queryMyComments(userId, page, pageSize));
    }
}
