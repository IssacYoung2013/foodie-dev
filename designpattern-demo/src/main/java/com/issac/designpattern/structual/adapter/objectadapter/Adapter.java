package com.issac.designpattern.structual.adapter.objectadapter;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Adapter implements Target {
    private Adaptee adaptee = new Adaptee();

    @Override
    public void request() {
        adaptee.adapteeRequest();
    }
}
