package com.issac.jvm.threadpool;

import java.util.Date;
import java.util.concurrent.*;

/**
 * @author: ywy
 * @date: 2021-02-09
 * @desc:
 */
public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
                10,
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
        // 延迟3秒之后执行任务
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaa");
            }
        }, 3, TimeUnit.SECONDS);

        ScheduledFuture<String> future = executor.schedule(new Callable<String>() {
                                                               @Override
                                                               public String call() throws Exception {
                                                                   return "bbb";
                                                               }
                                                           },
                4, TimeUnit.SECONDS);
        String s = future.get();
        System.out.println(s);

        executor.scheduleAtFixedRate(new Runnable() {
                                         @Override
                                         public void run() {
                                             System.out.println("scheduleAtFixedRate" + new Date());
                                             try {
                                                 Thread.sleep(1000L);
                                             } catch (InterruptedException e) {
                                                 e.printStackTrace();
                                             }
                                         }
                                     },
                // 第一次执行任务延迟多久
                0,
                // 每隔多久执行任务
                3,
                TimeUnit.SECONDS);

        executor.scheduleWithFixedDelay(new Runnable() {
                                            @Override
                                            public void run() {
                                                System.out.println("scheduleWithFixedDelay" + new Date());
                                                try {
                                                    Thread.sleep(1000L);
                                                } catch (InterruptedException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },
                // 第一次执行任务延迟多久
                0,
                // 每次执行完任务，延迟多久再次执行这个任务每隔多久执行任务
                3,
                TimeUnit.SECONDS);
    }
}
