package com.issac.pojo.vo;

import com.issac.pojo.bo.ShopCartItemBO;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
public class OrderVO {
    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;

    private List<ShopCartItemBO> toBeRemovedShopcartList;

    public List<ShopCartItemBO> getToBeRemovedShopcartList() {
        return toBeRemovedShopcartList;
    }

    public void setToBeRemovedShopcartList(List<ShopCartItemBO> toBeRemovedShopcartList) {
        this.toBeRemovedShopcartList = toBeRemovedShopcartList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}
