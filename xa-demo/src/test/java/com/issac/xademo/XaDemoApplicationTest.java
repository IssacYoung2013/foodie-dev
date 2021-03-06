package com.issac.xademo;

import com.issac.xademo.service.XaService;
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
public class XaDemoApplicationTest {

    @Autowired
    XaService xaService;

    @Test
    public void testXA() {
        xaService.testXA();
    }
}