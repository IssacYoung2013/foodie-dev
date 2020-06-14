package com.issac.service.center;

import com.issac.pojo.Orders;
import com.issac.pojo.vo.OrderStatusCountsVO;
import com.issac.util.PagedGridResult;

/**
 * @author: ywy
 * @date: 2020-06-06
 * @desc:
 */
public interface MyOrdersService {

    /**
     * 查询我的订单列表
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyOrders(String userId,
                                  Integer orderStatus,
                                  Integer page,
                                  Integer pageSize);

    /**
     * 商家发货
     * @param orderId
     */
    void updateDeliverOrderStatus(String orderId);

    /**
     * 查询我的订单
     * @param userId
     * @param orderId
     * @return
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 更新订单状态 确认收货
     * @param orderId
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 查询用户订单数
     * @param userId
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 查询订单变化趋势
     * @param userId
     * @return
     */
    PagedGridResult getOrderTrend(String userId, int page,int pageSize);
}
