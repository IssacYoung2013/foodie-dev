package com.issac.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@Controller
public class BaseController {

    public static final Integer COMMENT_PAGE_SIZE = 10;

    public static final String FOODIE_SHOPCART = "shopcart";

    /**
     * 支付中心地址
     */
    public static final String PAYMENT_URL = "http://localhost:8089/payment/createMerchantOrder";

    /**
     * 支付回调
     */
    public static final String PAY_RETURN_URL = "http://localhost:8088/orders/notifyMerchantOrderPaid";


    public static final String PAYMENT_ASYNC_RETURN_URL = "http://localhost:8089/payment/notice/mockPay";

    /**
     * 用户头像地址
     */
    public static final String IMG_USER_FACE_LOCATION = File.separator + "Users"
            + File.separator + "Issac" + File.separator + "workspaces" + File.separator + "images" +
            File.separator + "foodie-dev" + File.separator + "faces";
}
