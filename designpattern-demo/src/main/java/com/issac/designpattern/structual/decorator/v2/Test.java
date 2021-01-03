package com.issac.designpattern.structual.decorator.v2;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        ABattercake aBattercake;
        aBattercake = new Battercake();
        aBattercake = new EggDecorator(aBattercake);
        aBattercake = new EggDecorator(aBattercake);
        aBattercake = new SausageDecorator(aBattercake);

        System.out.println(aBattercake.getDesc() + ", cost:"+aBattercake.cose());
    }
}
