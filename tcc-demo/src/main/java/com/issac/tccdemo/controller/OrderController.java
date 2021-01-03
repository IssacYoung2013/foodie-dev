package com.issac.tccdemo.controller;

import com.issac.tccdemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@RestController
public class OrderController {

    @Autowired
    OrderService service;

    @RequestMapping("/handleOrder")
    public String handleOrder(int orderId) {
        try {
            int ret = service.handleOrder(orderId);
            if (ret == 0) {
                return "success";
            }
        } catch (Exception e) {

        }
        return "failure";
    }
}
