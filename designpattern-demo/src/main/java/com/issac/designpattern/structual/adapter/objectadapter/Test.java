package com.issac.designpattern.structual.adapter.objectadapter;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        Target target = new TargetImpl();
        target.request();

        Target adapter = new Adapter();
        adapter.request();
    }
}
