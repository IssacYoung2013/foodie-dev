package com.issac.jvm.lock.sync;

/**
 * @author: ywy
 * @date: 2021-02-18
 * @desc:
 */
public class SynchronizedTest4 {
    Object object = null;

    public SynchronizedTest4() {
        object = new Object();
    }

    private void someMethod() {
        synchronized (object) {
            System.out.println(object);
        }
    }
}
