package com.issac.tccdemo;

import com.issac.tccdemo.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TccDemoApplicationTest {

    @Autowired
    AccountService accountService;

    @Test
    public void testTrans() {
//        accountService.transferAccount();
    }
}