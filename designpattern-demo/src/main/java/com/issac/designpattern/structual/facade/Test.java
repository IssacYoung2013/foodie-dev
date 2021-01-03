package com.issac.designpattern.structual.facade;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        PointsGift pointsGift = new PointsGift();
        pointsGift.setName("T恤");
        GiftExchangeService giftExchangeService = new GiftExchangeService();

        giftExchangeService.giftExchange(pointsGift);
    }
}
