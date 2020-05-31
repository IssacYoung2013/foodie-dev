package com.issac.service.impl;

import com.issac.mapper.UsersMapper;
import com.issac.pojo.Users;
import com.issac.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Transactional(propagation= Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId, String pwd) {

        Users user = new Users();
        user.setImoocUserId(userId);
        user.setPassword(pwd);

        return usersMapper.selectOne(user);
    }

}