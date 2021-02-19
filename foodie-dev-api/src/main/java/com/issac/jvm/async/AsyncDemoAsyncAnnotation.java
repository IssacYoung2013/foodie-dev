package com.issac.jvm.async;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author: ywy
 * @date: 2021-02-17
 * @desc:
 */
@Component
public class AsyncDemoAsyncAnnotation {

    @Autowired
    AsyncJob asyncJob;

    private void subBiz1() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(new Date() + "subBiz1");
    }
    private void subBiz2() throws InterruptedException {
        Thread.sleep(1000L);
        System.out.println(new Date() + "subBiz2");
    }


    public void biz() throws InterruptedException, ExecutionException {
        this.subBiz1();
        Future<String> log = asyncJob.saveOpLog();
        this.subBiz2();
        String s = log.get();
        System.out.println("Async返回："+s);
        System.out.println(new Date() + "执行结束");
    }
}
