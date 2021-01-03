package com.issac.shardingjdbc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: ywy
 * @date: 2020-12-24
 * @desc:
 */
@SpringBootApplication
//@ImportResource("classpath*:sharding-jdbc.xml")
@MapperScan(basePackages = {"com.issac.shardingjdbc.demo.dao"})
public class ShardingJdbcApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcApplication.class);
    }
}
