package com.issac.service;


import com.issac.pojo.Users;
import com.issac.pojo.bo.UserBO;

/**
 * @author: ywy
 * @date: 2020-05-10
 * @desc:
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     * @Return boolean
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     * BO 前端传后端
     * @return
     */
    Users createUser(UserBO userBO);

    /**
     * 检索用户名密码是否匹配
     * @param username
     * @param password
     * @return
     */
    Users queryUserForLogin(String username, String password);
}
