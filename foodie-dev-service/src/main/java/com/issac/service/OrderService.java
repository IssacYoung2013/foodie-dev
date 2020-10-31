package com.issac.service;

import com.issac.pojo.OrderStatus;
import com.issac.pojo.Orders;
import com.issac.pojo.bo.ShopCartItemBO;
import com.issac.pojo.bo.SubmitOrderBO;
import com.issac.pojo.vo.OrderVO;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-16
 * @desc:
 */
public interface OrderService {

    /**
     * 创建订单相关信息
     * @param shopCartList
     * @param submitOrderBO
     */
    OrderVO createOrder(List<ShopCartItemBO> shopCartList,
                        SubmitOrderBO submitOrderBO);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    Orders queryOrdersByOrderId(String orderId);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
