package com.issac.jvm.lock.sync;

import java.util.List;
import java.util.Vector;

/**
 * @author: ywy
 * @date: 2021-02-18
 * @desc:
 */
public class SynchronizedTest2 {

    private static List<Integer> list = new Vector<>();

    /**
     * 关闭偏向锁：554
     * 开启偏向锁：386
     * @param args
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            list.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
