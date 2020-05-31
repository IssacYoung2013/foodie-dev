package com.issac.wx.service;

import com.issac.wx.entity.PayResult;
import com.issac.wx.entity.PreOrderResult;

import java.io.InputStream;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
public interface WxOrderService {

    /**
     *
     * @Description: 调用微信接口进行统一下单
     * @param body
     * @param out_trade_no
     * @param total_fee
     * @return
     * @throws Exception
     *
     * @author leechenxiang
     * @date 2017年8月31日 下午2:56:56
     */
    public PreOrderResult placeOrder(String body, String out_trade_no, String total_fee) throws Exception;

    /**
     *
     * @Description: 获取支付结果
     * @return
     * @throws Exception
     *
     * @author leechenxiang
     * @date 2017年9月1日 上午8:57:53
     */
    public PayResult getWxPayResult(InputStream inStream) throws Exception;

}
