package com.issac.service;

import com.issac.pojo.Users;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
public interface UserService {

    /**
     * @Description: 查询用户信息
     */
    public Users queryUserInfo(String userId, String pwd);

}
