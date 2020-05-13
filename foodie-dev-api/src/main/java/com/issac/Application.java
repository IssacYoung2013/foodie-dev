package com.issac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@SpringBootApplication
// 扫描mybatis通用mapper所在的包
@MapperScan(basePackages = "com.issac.mapper")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.issac","org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
