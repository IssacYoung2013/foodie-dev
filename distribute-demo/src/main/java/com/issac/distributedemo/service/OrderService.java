package com.issac.distributedemo.service;

import com.issac.distributedemo.dao.OrderItemMapper;
import com.issac.distributedemo.dao.OrderMapper;
import com.issac.distributedemo.dao.ProductMapper;
import com.issac.distributedemo.model.Order;
import com.issac.distributedemo.model.OrderItem;
import com.issac.distributedemo.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: ywy
 * @date: 2020-12-17
 * @desc:
 */
@Slf4j
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private ProductMapper productMapper;

    private int purchaseProductId = 100100;

    private int purchaseProductNum = 1;

    private Lock lock = new ReentrantLock();

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Resource
    TransactionDefinition transactionDefinition;

    //    @Transactional(rollbackFor = Exception.class)
    public synchronized Integer createOrder() throws Exception {
        Product product;
        try {

            lock.lock();
//        synchronized (this) {
            TransactionStatus transaction1 = platformTransactionManager.getTransaction(transactionDefinition);

            product = productMapper.selectByPrimaryKey(purchaseProductId);
            if (product == null) {
                platformTransactionManager.rollback(transaction1);
                throw new Exception("购买商品：" + purchaseProductId + "不存在！");
            }
            // 商品当前库存
            Integer currentCount = product.getCount();
            // 检验库存
            if (purchaseProductNum > currentCount) {
                platformTransactionManager.rollback(transaction1);
                throw new Exception("商品：" + purchaseProductId + "仅剩，" + currentCount + "件，无法购买");
            }
            // 计算剩余库存
//        int leftCount = currentCount - purchaseProductNum;
//        // 更新库存
//        product.setCount(leftCount);
//        product.setUpdateTime(new Date());
//        productMapper.updateByPrimaryKeySelective(product);
            // 超卖第一个现象：通过update行锁解决超卖问题
            productMapper.updateProductCount(purchaseProductNum, "xxx", new Date(), purchaseProductId);
            platformTransactionManager.commit(transaction1);
        } finally {

            lock.unlock();
        }
//        }


        // 检索商品的库存
        // 如果商品为负数，抛出异常
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);

        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        order.setOrderStatus(1);
        order.setReceiverName("xxx");
        order.setReceiverMobile("1324567890");
        order.setCreateTime(new Date());
        order.setCreateUser("xxx");
        order.setUpdateTime(new Date());
        orderMapper.insertSelective(order);

        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateTime(new Date());
        orderItem.setCreateUser("xxx");
        orderItem.setUpdateTime(new Date());
        orderItemMapper.insertSelective(orderItem);
        platformTransactionManager.commit(transaction);
        return order.getId();
    }
}
