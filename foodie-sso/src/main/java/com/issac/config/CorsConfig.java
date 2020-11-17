package com.issac.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author: ywy
 * @date: 2020-05-15
 * @desc: 跨域
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (factory) -> factory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:8080");
        config.addAllowedOrigin("http://localhost:8090");

        config.addAllowedOrigin("http://shop.z.mukewang.com:8080");
        config.addAllowedOrigin("http://shop.z.mukewang.com:90");
        config.addAllowedOrigin("http://api.z.mukewang.com:8088");
        config.addAllowedOrigin("http://center.z.mukewang.com:8080");
        config.addAllowedOrigin("http://music.a.com");
        config.addAllowedOrigin("http://mtv.a.com");
        config.addAllowedOrigin("http://music.a.com:8080");
        config.addAllowedOrigin("http://mtv.a.com:8080");
        config.addAllowedOrigin("http://sso.a.com");
        config.addAllowedOrigin("http://sso.a.com:8090");

//        config.addAllowedOrigin("*");
        // 设置是否发送cookie信息
        config.setAllowCredentials(true);

        // 设置允许请求的方式
        config.addAllowedMethod("*");

        // 设置允许的header
        config.addAllowedHeader("*");

        // 2. 为url添加映射路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**",config);

        // 3. 返回重新定义好的source
        return new CorsFilter(corsSource);
    }
}
