package com.issac.designpattern.behavioral.interpreter;

/**
 * @author: ywy
 * @date: 2020-12-28
 * @desc:
 */
public class NumberInterpreter implements Interpreter{
    private int num;

    public NumberInterpreter(int num) {
        this.num = num;
    }

    public NumberInterpreter(String str) {
        this.num = Integer.parseInt(str);
    }

    @Override
    public int interpret() {
        return this.num;
    }
}
