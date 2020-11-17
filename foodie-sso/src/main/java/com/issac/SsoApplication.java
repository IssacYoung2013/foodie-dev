package com.issac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@SpringBootApplication
@MapperScan(basePackages = "com.issac.mapper")
@ComponentScan(basePackages = {"com.issac", "org.n3r.idworker"})
public class SsoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }
}
