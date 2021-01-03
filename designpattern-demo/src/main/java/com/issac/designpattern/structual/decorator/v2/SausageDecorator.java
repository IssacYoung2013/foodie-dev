package com.issac.designpattern.structual.decorator.v2;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class SausageDecorator extends AbstactDecorator {
    public SausageDecorator(ABattercake aBattercake) {
        super(aBattercake);
    }

    @Override
    protected String getDesc() {
        doSomething();
        return super.getDesc() + " 加一根香肠";
    }

    @Override
    protected int cose() {
        return super.cose() + 2;
    }

    @Override
    protected void doSomething() {
        System.out.println("切香肠");
    }
}
