package com.issac.designpattern.behavioral.interpreter;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        String expressionStr = "2 3 5 + *";
        String str = "{{apiName.apiProperty1}} 测试 {{apiName.apiProperty2}}";
        ExpressionParser expressionParser = new ExpressionParser();
        int result = expressionParser.parse(expressionStr);
        System.out.println("解释器计算结果:" + result);

        org.springframework.expression.ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("100 * 2 + 400 * 1 + 66");
        Object value = expression.getValue();
        System.out.println(value);
    }
}
