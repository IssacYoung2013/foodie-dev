package com.issac.designpattern.behavioral.interpreter;

import java.util.Stack;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class ExpressionParser {
    private Stack<Interpreter> stack = new Stack<>();

    public int parse(String str) {
        String[] strItemArray = str.split(" ");
        for (String symbol : strItemArray) {
            if (!OperatorUtil.isOperator(symbol)) {
                Interpreter numExpression = new NumberInterpreter(symbol);
                stack.push(numExpression);
                System.out.println(String.format("入栈：%d", numExpression.interpret()));
            } else {
                // 运算符号可以计算
                Interpreter firstExpression = stack.pop();
                Interpreter secondExpression = stack.pop();
                System.out.println(String.format("出栈： %d 和 %d", firstExpression.interpret(), secondExpression.interpret()));
                Interpreter operator = OperatorUtil.getExpressionObject(firstExpression, secondExpression, symbol);
                System.out.println(String.format("应用运算符： %s", operator));
                int result = operator.interpret();
                NumberInterpreter resultExpress = new NumberInterpreter(result);
                stack.push(resultExpress);
                System.out.println(String.format("阶段结果入栈：%d", result));
            }
        }
        return stack.pop().interpret();
    }
}
