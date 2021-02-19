package com.issac.jvm.async;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: ywy
 * @date: 2021-02-17
 * @desc:
 */
public class AsyncDemoThreadPool {

    ThreadPoolExecutor executor = new ThreadPoolExecutor(
            // 核心线程正式员工，非核心线程临时工
            10,
            10,
            10L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            // 人才市场
            Executors.defaultThreadFactory(),
            // 任务结束；默认解雇临时工
            new ThreadPoolExecutor.AbortPolicy()
    );

    private void subBiz1() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(new Date() + "subBiz1");
    }
    private void subBiz2() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(new Date() + "subBiz2");
    }
    private void saveOpLog() throws InterruptedException {
        executor.submit(new SaveOpLogThread2());
    }

    private void biz() throws InterruptedException {
        this.subBiz1();
        this.saveOpLog();
        this.subBiz2();

        System.out.println(new Date() + "执行结束");
    }

    public static void main(String[] args) throws InterruptedException {
        new AsyncDemoThreadPool().biz();
    }
}

class SaveOpLogThread2 implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date() + "插入操作日志");
    }
}
