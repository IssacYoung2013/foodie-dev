package com.issac.designpattern.structual.decorator.v1;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class BattercakeWithEggSausage extends BattercakeWithEgg{
    @Override
    public String getDesc() {
        return super.getDesc()+ "加香肠";
    }
    @Override
    public int cose() {
        return super.cose() + 2;
    }
}
