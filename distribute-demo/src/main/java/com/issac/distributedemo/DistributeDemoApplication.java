package com.issac.distributedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: ywy
 * @date: 2020-12-17
 * @desc:
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.issac.distributedemo.dao"})
public class DistributeDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributeDemoApplication.class,args);
    }
}
