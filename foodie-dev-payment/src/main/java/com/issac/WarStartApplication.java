package com.issac;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
public class WarStartApplication
//        extends SpringBootServletInitializer
{

//    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        // 指向Application这个spingboot的启动类
        return builder.sources(PayApplication.class);
    }
}