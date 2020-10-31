package com.issac;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: ywy
 * @date: 2020-07-26
 * @desc: 打包war [4] 添加war启动类
 */
public class WarStarterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 指向Application这个springboot 启动类
        return builder.sources(Application.class);
    }
}
