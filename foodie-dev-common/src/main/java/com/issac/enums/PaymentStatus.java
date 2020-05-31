package com.issac.enums;

/**
 * @author: ywy
 * @date: 2020-05-13
 * @desc: 订单状态
 */
public enum PaymentStatus {
    WAIT_PAY(10,"待付款"),
    PAID(20,"已付款"),
    WAIT_RECEIVE(30,"待收货"),
    SUCCESS(40,"交易成功"),
    CLOSE(50,"交易关闭");

    public final Integer type;

    public final String value;

    PaymentStatus(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
