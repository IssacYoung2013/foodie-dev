package com.test;

import com.issac.Application;
import com.issac.service.StuService;
import com.issac.service.TestTransService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: ywy
 * @date: 2020-05-10
 * @desc:
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
public class TransTest {

    @Resource
    StuService stuService;

    @Resource
    TestTransService testTransService;

//    @Test
    public void mytest() {
        testTransService.testPropagationTrans();
    }
}
