package com.issac.jvm.lock.reen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: ywy
 * @date: 2021-02-19
 * @desc:
 */
public class ReentrantLockTest3 {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReentrantLockTest3.class);
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();

    public void awaitCondition1() {
        String threadName = Thread.currentThread().getName();
        try {
            lock.lock();
            LOGGER.info("{} {} 调用了awaitCondition1", new Date(), threadName);
            long beforeTime = System.currentTimeMillis();
            // 阻塞注册了 condition1 的线程
            condition1.await();
            long afterTime = System.currentTimeMillis();
            LOGGER.info("{} {} 被唤醒", new Date(), threadName);
            LOGGER.info("{} {} 等待了：{}ms", new Date(), threadName, afterTime - beforeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void awaitCondition2() {
        String threadName = Thread.currentThread().getName();
        try {
            lock.lock();
            LOGGER.info("{} {} 调用了awaitCondition2", new Date(), threadName);
            long beforeTime = System.currentTimeMillis();
            // 阻塞注册了 condition2 的线程
            condition2.await();
            long afterTime = System.currentTimeMillis();
            LOGGER.info("{} {} 被唤醒", new Date(), threadName);
            LOGGER.info("{} {} 等待了：{}ms", new Date(), threadName, afterTime - beforeTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalCondition1() {
        try {
            lock.lock();
            LOGGER.info("{} {}启动唤醒程序", new Date(), Thread.currentThread().getName());
            condition1.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void signalCondition2() {
        try {
            lock.lock();
            LOGGER.info("{} {}启动唤醒程序", new Date(), Thread.currentThread().getName());
            condition2.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest3 test3 = new ReentrantLockTest3();
        new Thread(test3::awaitCondition1).start();
        new Thread(test3::awaitCondition2).start();

        Thread.sleep(1000L);
        test3.signalCondition1();

        Thread.sleep(1000L);
        test3.signalCondition2();
    }
}
