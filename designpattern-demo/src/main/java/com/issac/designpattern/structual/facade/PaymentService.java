package com.issac.designpattern.structual.facade;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class PaymentService {
    public boolean pay(PointsGift pointsGift) {
        System.out.println("支付"+pointsGift.getName()+" 成功");
        return true;
    }
}
