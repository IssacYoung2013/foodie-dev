package com.issac.designpattern.structual.adapter.objectadapter;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class TargetImpl implements Target {
    @Override
    public void request() {
        System.out.println("目标实现方法");
    }
}
