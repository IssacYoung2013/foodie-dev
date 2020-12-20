package com.issac.distributedemo;

import com.issac.distributedemo.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

import static org.junit.Assert.*;

/**
 * @author: ywy
 * @date: 2020-12-17
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DistributeDemoApplicationTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void concurrentOrder() throws InterruptedException, BrokenBarrierException {
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Integer orderId = orderService.createOrder();
                    System.out.println("订单id:" + orderId);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });
        }
        cdl.await();
        es.shutdown();
    }
}