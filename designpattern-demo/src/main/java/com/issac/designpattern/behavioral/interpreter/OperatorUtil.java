package com.issac.designpattern.behavioral.interpreter;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class OperatorUtil {
    public static boolean isOperator(String symbol) {
        return symbol.equals("+") || symbol.equals("*");
    }

    public static Interpreter getExpressionObject(Interpreter firstExpression, Interpreter secondExpression, String symbol) {
        if ("+".equals(symbol)) {
            return new AddInterpreter(firstExpression, secondExpression);
        } else if ("*".equals(symbol)) {
            return new MultiInterpreter(firstExpression, secondExpression);
        }
        return null;
    }
}
