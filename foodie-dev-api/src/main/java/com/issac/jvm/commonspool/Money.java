package com.issac.jvm.commonspool;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

/**
 * @author: ywy
 * @date: 2021-02-02
 * @desc:
 */
public class Money {
    public static Money init() {
        try {
            TimeUnit.MILLISECONDS.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Money("USD",new BigDecimal(1));
    }

    private String type;

    private BigDecimal amount;

    public Money(String type, BigDecimal amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
