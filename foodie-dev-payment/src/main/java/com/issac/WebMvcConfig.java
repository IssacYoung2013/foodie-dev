package com.issac;

import com.issac.controller.interceptor.PayCenterInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: ywy
 * @date: 2020-05-30
 * @desc:
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public PayCenterInterceptor payCenterInterceptor() {
        return new PayCenterInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(payCenterInterceptor())
                .addPathPatterns("/payment/*");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
