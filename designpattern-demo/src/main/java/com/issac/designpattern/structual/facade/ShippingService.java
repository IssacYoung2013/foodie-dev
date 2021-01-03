package com.issac.designpattern.structual.facade;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class ShippingService {
    public String shippingGift(PointsGift pointsGift) {
        System.out.println("运输"+pointsGift.getName()+" 成功");
        return "666";
    }
}
