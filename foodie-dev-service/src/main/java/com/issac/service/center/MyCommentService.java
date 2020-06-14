package com.issac.service.center;

import com.issac.pojo.OrderItems;
import com.issac.pojo.bo.center.OrderItemsCommentBO;
import com.issac.util.PagedGridResult;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-06-10
 * @desc:
 */
public interface MyCommentService {

    /**
     * 根据订单id查询管理的商品
     *
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 评价商品
     *
     * @param orderId
     * @param userId
     * @param comments
     */
    void saveComments(String orderId, String userId, List<OrderItemsCommentBO> comments);

    /**
     * 查询我的评价列表
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
