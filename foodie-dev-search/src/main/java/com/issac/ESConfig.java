package com.issac;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author: ywy
 * @date: 2020-11-14
 * @desc:
 */
@Configuration
public class ESConfig {

    /**
     * 解决netty引起的issue
     */
    @PostConstruct
    void init() {
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }
}
