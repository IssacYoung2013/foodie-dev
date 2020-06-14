package com.issac.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.issac.enums.OrderStatusEnum;
import com.issac.enums.YesOrNo;
import com.issac.mapper.OrderStatusMapper;
import com.issac.mapper.OrdersCustomMapper;
import com.issac.mapper.OrdersMapper;
import com.issac.pojo.OrderStatus;
import com.issac.pojo.Orders;
import com.issac.pojo.vo.MyOrdersVO;
import com.issac.pojo.vo.OrderStatusCountsVO;
import com.issac.service.center.MyOrdersService;
import com.issac.util.PagedGridResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: ywy
 * @date: 2020-06-06
 * @desc:
 */
@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Resource
    OrdersCustomMapper ordersCustomMapper;

    @Resource
    OrderStatusMapper orderStatusMapper;

    @Resource
    OrdersMapper ordersMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> list = ordersCustomMapper.queryMyOrders(map);
        return setterPagedGrid(list, page);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Orders queryMyOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setUserId(userId);
        orders.setIsDelete(YesOrNo.NO.type);
        return ordersMapper.selectOne(orders);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        orderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int rows = orderStatusMapper.updateByExampleSelective(orderStatus, example);
        return rows == 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean deleteOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setIsDelete(YesOrNo.YES.type);
        orders.setUpdatedTime(new Date());
        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", orderId);
        criteria.andEqualTo("userId", userId);
        int rows = ordersMapper.updateByExampleSelective(orders, example);
        return rows == 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        orderStatus.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        orderStatusMapper.updateByExampleSelective(orderStatus, example);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public OrderStatusCountsVO getOrderStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersCustomMapper.getMyOrderStatusCounts(map);

        OrderStatusCountsVO countsVO = new OrderStatusCountsVO(waitPayCounts, waitDeliverCounts
                , waitReceiveCounts, waitCommentCounts);
        return countsVO;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult getOrderTrend(String userId, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        return setterPagedGrid(ordersCustomMapper.getMyOrderTrend(map), page);
    }
}
