package com.issac.jvm.threadpool;

import java.util.concurrent.*;

/**
 * @author: ywy
 * @date: 2021-02-09
 * @desc:
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程池测试");
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程池测试2");
            }
        });
        Future<String> future = executor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "测试submit";
            }
        });
        String s = future.get();
        System.out.println(s);
    }
}
