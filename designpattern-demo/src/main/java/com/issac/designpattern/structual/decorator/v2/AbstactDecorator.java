package com.issac.designpattern.structual.decorator.v2;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public abstract class AbstactDecorator extends ABattercake {
    private ABattercake aBattercake;

    public AbstactDecorator(ABattercake aBattercake) {
        this.aBattercake = aBattercake;
    }

    protected abstract void doSomething();

    @Override
    protected String getDesc() {
        return this.aBattercake.getDesc();
    }

    @Override
    protected int cose() {
        return this.aBattercake.cose();
    }
}
