package com.issac.designpattern.structual.adapter;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        DC5 dc5 = new PowerAdapter();
        dc5.outputDCV();
    }
}
