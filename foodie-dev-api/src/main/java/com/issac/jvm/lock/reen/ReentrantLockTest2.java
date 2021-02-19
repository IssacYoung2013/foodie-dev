package com.issac.jvm.lock.reen;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: ywy
 * @date: 2021-02-18
 * @desc:
 */
public class ReentrantLockTest2 {
    public static void main(String[] args) {
        FairTest test = new FairTest();
        Thread t1 = new Thread(test);
        Thread t2 = new Thread(test);
        Thread t3 = new Thread(test);

        t1.start();
        t2.start();
        t3.start();
    }
}


class FairTest implements Runnable {
    private ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "开始运行");
            lock.lock();

            System.out.println(Thread.currentThread().getName() + "拿到锁");
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            System.out.println("Sleep发生异常");
        } finally {
            System.out.println(Thread.currentThread().getName() + "释放锁");
            lock.unlock();
        }
    }
}