package com.issac.designpattern.structual.decorator.v2;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class EggDecorator extends AbstactDecorator {
    public EggDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }

    @Override
    protected String getDesc() {
        return super.getDesc() + "加一个鸡蛋";
    }

    @Override
    protected int cose() {
        return super.cose() + 1;
    }

    @Override
    protected void doSomething() {
        System.out.println("敲鸡蛋");
    }
}
