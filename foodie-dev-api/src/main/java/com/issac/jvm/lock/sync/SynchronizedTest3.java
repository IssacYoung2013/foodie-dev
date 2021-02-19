package com.issac.jvm.lock.sync;

/**
 * @author: ywy
 * @date: 2021-02-18
 * @desc:
 */
public class SynchronizedTest3 {
    public static void main(String[] args) {
        someMethod();
    }

    private static void someMethod() {
        Object object = new Object();
        synchronized (object) {
            System.out.println(object);
        }
    }
}
