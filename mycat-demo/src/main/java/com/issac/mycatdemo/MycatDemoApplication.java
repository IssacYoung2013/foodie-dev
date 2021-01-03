package com.issac.mycatdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: ywy
 * @date: 2021-01-02
 * @desc:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.issac.mycatdemo.dao")
public class MycatDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MycatDemoApplication.class);
    }
}
