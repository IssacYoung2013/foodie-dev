package com.issac.jvm.threadpool;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: ywy
 * @date: 2021-02-09
 * @desc:
 */
public class LinkedBlockingQueueTest {
    public static void main(String[] args) {
        LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue(1);
        queue.add("abc");
        boolean def = queue.offer("def");
        System.out.println(def);
        queue.add("g");
    }
}
