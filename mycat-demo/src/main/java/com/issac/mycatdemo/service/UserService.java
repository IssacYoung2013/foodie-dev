package com.issac.mycatdemo.service;

import com.issac.mycatdemo.dao.UserMapper;
import com.issac.mycatdemo.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    public void testUser() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("奇数");
        userMapper.insert(user1);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("偶数123");
        userMapper.insert(user2);
    }
}
