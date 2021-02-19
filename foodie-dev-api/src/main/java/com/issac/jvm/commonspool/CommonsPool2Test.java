package com.issac.jvm.commonspool;

import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * @author: ywy
 * @date: 2021-02-02
 * @desc:
 */
public class CommonsPool2Test {
    public static void main(String[] args) throws Exception {
        GenericObjectPool<Money> objectPool = new GenericObjectPool<>(new MoneyPooledObjectFactory());
        Money money = objectPool.borrowObject();
        money.setType("RMB");
        objectPool.returnObject(money);
    }
}
