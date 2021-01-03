package com.issac.designpattern.structual.facade;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class QualifyService {
    public boolean isAvaliable(PointsGift pointsGift) {
        System.out.println("校验"+pointsGift.getName()+" 积分资格通过");
        return true;
    }
}
