package com.issac.designpattern.structual.adapter.classadapter;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Adapter extends Adaptee implements Target {
    @Override
    public void request() {
        // 逻辑
        super.adapteeRequest();
    }
}
