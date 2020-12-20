package com.issac.distributelock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: ywy
 * @date: 2020-12-18
 * @desc:
 */
@SpringBootApplication
@ImportResource("classpath:redisson.xml")
//@EnableScheduling
public class DistributeLockApplication {
    public static void main(String[] args) {
        SpringApplication.run(DistributeLockApplication.class);
    }
}
