package com.issac.shardingjdbc.demo;

import com.issac.shardingjdbc.demo.dao.OrderItemMapper;
import com.issac.shardingjdbc.demo.dao.OrderMapper;
import com.issac.shardingjdbc.demo.model.Order;
import com.issac.shardingjdbc.demo.model.OrderExample;
import com.issac.shardingjdbc.demo.model.OrderItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: ywy
 * @date: 2020-12-24
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ShardingJdbcApplicationTest {

    @Resource
    OrderMapper orderMapper;

    @Resource
    OrderItemMapper orderItemMapper;

    @Test
    @Transactional
    public void testOrder() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setUserId(25);
        order.setOrderAmount(10);
        order.setOrderStatus(1);

        orderMapper.insertSelective(order);

        Order order2 = new Order();
        order2.setOrderId(2L);
        order2.setUserId(26);
        order2.setOrderAmount(10);
        order2.setOrderStatus(1);

        orderMapper.insertSelective(order2);

        throw new RuntimeException("text XA");
    }

    @Test
    public void testSelectOrder() {
        OrderExample orderExample = new OrderExample();
        List<Order> orders = orderMapper.selectByExample(orderExample);
        orders.forEach(System.out::println);
    }

    @Test
    public void testInsertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(2);
        orderItem.setOrderId(1);
        orderItem.setProductName("测试商品");
        orderItem.setNum(1);
        orderItem.setUserId(19);
        orderItemMapper.insert(orderItem);
    }
}