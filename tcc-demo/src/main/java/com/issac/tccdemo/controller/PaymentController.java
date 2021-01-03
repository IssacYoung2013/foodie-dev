package com.issac.tccdemo.controller;

import com.issac.tccdemo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("payment")
    public String payment(int userId, int orderId, BigDecimal amount) throws Exception {
        int result = paymentService.paymentMQ(userId, orderId, amount);
        return "支付结果：" + result;
    }
}
