package com.issac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */

@MapperScan(basePackages = "com.issac.mapper")
@ComponentScan(basePackages = {"com.issac","org.n3r.idworker"})
@SpringBootApplication
public class FsApplication {
    public static void main(String[] args) {
        SpringApplication.run(FsApplication.class, args);
    }
}
