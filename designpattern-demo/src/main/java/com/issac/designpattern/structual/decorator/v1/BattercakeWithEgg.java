package com.issac.designpattern.structual.decorator.v1;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class BattercakeWithEgg extends Battercake{
    @Override
    public String getDesc() {
        return super.getDesc()+ "加蛋";
    }
    @Override
    public int cose() {
        return super.cose() + 1;
    }
}
