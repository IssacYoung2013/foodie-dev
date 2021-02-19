package com.issac.jvm.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: ywy
 * @date: 2021-02-10
 * @desc:
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("aaa");
            }
        });
    }
}
