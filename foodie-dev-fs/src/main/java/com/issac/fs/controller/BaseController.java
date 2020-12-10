package com.issac.fs.controller;

import com.issac.pojo.Orders;
import com.issac.pojo.Users;
import com.issac.pojo.vo.UsersVO;
import com.issac.service.center.MyOrdersService;
import com.issac.util.JSONResult;
import com.issac.util.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.util.UUID;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@Controller
public class BaseController {

    public static final Integer COMMENT_PAGE_SIZE = 10;

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    @Resource
    RedisOperator redisOperator;

    /**
     * 支付中心地址
     */
    public static final String PAYMENT_URL =
//            "http://payment.t.mukewang.com" +
            "http://localhost" +
                    ":8089/" +
//                    "foodie-payment" +
                    "/payment/createMerchantOrder";

    /**
     * 支付回调
     */
    public static final String PAY_RETURN_URL =
//            "http://api.z.mukewang.com" +
            "http://localhost" +
                    ":8088/" +
//                    "foodie-dev-api" +
                    "/orders/notifyMerchantOrderPaid";


    public static final String PAYMENT_ASYNC_RETURN_URL =
//            "http://payment.t.mukewang.com" +
            "http://localhost" +
                    ":8089/" +
//                    "foodie-payment" +
                    "/payment/notice/mockPay";

    /**
     * 用户头像地址
     */
    public static final String IMG_USER_FACE_LOCATION = File.separator + "Users"
            + File.separator + "Issac" + File.separator + "workspaces" + File.separator + "images" +
            File.separator + "foodie-dev" + File.separator + "faces";

    @Resource
    protected MyOrdersService myOrdersService;

    protected JSONResult checkUserOrder(String userId, String orderId) {
        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (orders == null) {
            return JSONResult.errorMsg("订单不存在");
        }
        return JSONResult.ok(orders);
    }


    protected UsersVO convertUsersVO(Users users) {
        // 实现用户的redis会话
        String uniqueToken = UUID.randomUUID().toString().trim();
        redisOperator.set(REDIS_USER_TOKEN + ":" + users.getId(), uniqueToken);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserUniqueToken(uniqueToken);
        return usersVO;
    }
}
