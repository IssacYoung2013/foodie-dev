package com.issac.designpattern.structual.adapter;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class PowerAdapter implements DC5 {
    private AC220 ac220 = new AC220();
    @Override
    public int outputDCV() {
        int adapterInput = ac220.outputAC220V();
        // 变压器
        int output = adapterInput / 4;
        System.out.println("使用电源适配器输入ac："+adapterInput+"，输出："+output);
        return output;
    }
}
