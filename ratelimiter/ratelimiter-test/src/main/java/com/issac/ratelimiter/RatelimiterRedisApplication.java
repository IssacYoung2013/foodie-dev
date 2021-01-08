package com.issac.ratelimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: ywy
 * @date: 2021-01-06
 * @desc:
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.issac.*"})
public class RatelimiterRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(RatelimiterRedisApplication.class);
    }
}
