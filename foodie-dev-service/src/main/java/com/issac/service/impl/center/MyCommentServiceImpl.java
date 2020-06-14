package com.issac.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.issac.enums.YesOrNo;
import com.issac.mapper.ItemsCommentsCustomMapper;
import com.issac.mapper.OrderItemsMapper;
import com.issac.mapper.OrderStatusMapper;
import com.issac.mapper.OrdersMapper;
import com.issac.pojo.OrderItems;
import com.issac.pojo.OrderStatus;
import com.issac.pojo.Orders;
import com.issac.pojo.bo.center.OrderItemsCommentBO;
import com.issac.pojo.vo.MyCommentVO;
import com.issac.service.center.MyCommentService;
import com.issac.util.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2020-06-10
 * @desc:
 */
@Service
public class MyCommentServiceImpl extends BaseService implements MyCommentService {

    @Resource
    OrderItemsMapper orderItemsMapper;

    @Resource
    ItemsCommentsCustomMapper itemsCommentsCustomMapper;

    @Resource
    OrdersMapper ordersMapper;

    @Resource
    OrderStatusMapper orderStatusMapper;

    @Resource
    Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);

        return orderItemsMapper.select(query);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> comments) {
        // 1. 保存评价 item_comments
        for (OrderItemsCommentBO oic : comments) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", comments);
        itemsCommentsCustomMapper.saveComments(map);
        // 2. 修改订单表已评价
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.type);
        ordersMapper.updateByPrimaryKeySelective(orders);
        // 3. 订单状态表更新留言时间
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        List<MyCommentVO> list = itemsCommentsCustomMapper.queryMyComments(map);
        PageHelper.startPage(page, pageSize);
        return setterPagedGrid(list, page);
    }
}
