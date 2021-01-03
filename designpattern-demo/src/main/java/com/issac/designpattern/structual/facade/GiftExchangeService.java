package com.issac.designpattern.structual.facade;

/**
 * @author: ywy
 * @date: 2020-12-30
 * @desc:
 */
public class GiftExchangeService {
    private QualifyService qualifyService = new QualifyService();
    private PaymentService paymentService = new PaymentService();
    private ShippingService shippingService = new ShippingService();

    public void giftExchange(PointsGift pointsGift) {
        if(qualifyService.isAvaliable(pointsGift)) {
            if(paymentService.pay(pointsGift)) {
                String shippingOrderNo = shippingService.shippingGift(pointsGift);
                System.out.println("物流系统下单成功！订单号："+shippingOrderNo);
            }
        }
    }
}
