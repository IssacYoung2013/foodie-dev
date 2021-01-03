package com.issac.designpattern.structual.decorator.v2;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Battercake extends ABattercake {
    @Override
    protected String getDesc() {
        return "煎饼";
    }
    @Override
    protected int cose() {
        return 8;
    }
}
