package com.issac.service;


import com.issac.pojo.UserAddress;
import com.issac.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author: ywy
 * @date: 2020-05-16
 * @desc:
 */
public interface AddressService {

    /**
     * 根据用户id查询收货地址列表
     *
     * @param userId
     * @return
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 新增收货地址
     *
     * @param addressBO
     */
    void addNewUserAddress(AddressBO addressBO);

    /**
     * 用户修改地址
     *
     * @param addressBO
     */
    void updateUserAddress(AddressBO addressBO);

    /**
     * 用户删除地址
     *
     * @param userId
     * @param addressId
     */
    void removeUserAddress(String userId, String addressId);

    /**
     * 修改默认地址
     *
     * @param userId
     * @param addressId
     */
    void setDefaultAddress(String userId, String addressId);

    /**
     * 根据用户id地址id查询收货地址
     *
     * @param userId
     * @param addressId
     * @return
     */
    UserAddress queryUserAddress(String userId, String addressId);
}
