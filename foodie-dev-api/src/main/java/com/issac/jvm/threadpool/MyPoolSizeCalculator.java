package com.issac.jvm.threadpool;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: ywy
 * @date: 2021-02-17
 * @desc:
 */
public class MyPoolSizeCalculator extends PoolSizeCalculator {

    public static void main(String[] args) {
        MyPoolSizeCalculator calculator = new MyPoolSizeCalculator();
        // 1.0 cpu 目标利用率
        // blocking占用内存大小，byte
        calculator.calculateBoundaries(new BigDecimal(1.0),
                new BigDecimal(100000));
    }

    @Override
    protected long getCurrentThreadCPUTime() {
        // 当前线程占用的总时间
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    @Override
    protected Runnable creatTask() {
        return new AsynchronousTask();
    }

    @Override
    protected BlockingQueue createWorkQueue() {
        return new LinkedBlockingQueue<>();
    }

}

class AsynchronousTask implements Runnable {
    @Override
    public void run() {
//        System.out.println(Thread.currentThread().getName());
    }
}
