package com.issac.tccdemo.service;

import com.issac.tccdemo.db107.dao.OrderMapper;
import com.issac.tccdemo.db107.model.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@Service
public class OrderService {
    @Resource
    private OrderMapper orderMapper;

    public int handleOrder(int orderId) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (order == null) {
            return 1;
        }
        // 已支付
        order.setOrderStatus(1);
        order.setUpdateTime(new Date());
        order.setUpdateUser(0);
        orderMapper.updateByPrimaryKey(order);
        return 0;
    }
}
