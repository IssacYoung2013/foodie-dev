package com.issac.enums;

/**
 * @author: ywy
 * @date: 2020-05-13
 * @desc: 支付方式
 */
public enum PayMethod {
    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝");

    public final Integer type;

    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
