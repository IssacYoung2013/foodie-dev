package com.issac;

import com.issac.jvm.commonspool.datasource.DataSourceEndpoint;
import com.issac.jvm.commonspool.datasource.MyDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.nio.charset.Charset;

/**
 * @author: ywy
 * @date: 2020-05-08
 * @desc:
 */
@SpringBootApplication
//        (exclude = {SecurityAutoConfiguration.class})
// 扫描mybatis通用mapper所在的包
@MapperScan(basePackages = "com.issac.mapper")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.issac","org.n3r.idworker"})
@EnableScheduling
// 开启redis作为session
//@EnableRedisHttpSession
@EnableDiscoveryClient
@EnableAsync
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    public MyDataSource dataSource() {
        return new MyDataSource();
    }

    @Bean
    public DataSourceEndpoint dataSourceEndpoint() {
        DataSource dataSource = this.dataSource();
        return new DataSourceEndpoint((MyDataSource) dataSource);
    }

    @Bean
    public AsyncRestTemplate asyncRestTemplate() {
        AsyncRestTemplate restTemplate = new AsyncRestTemplate();
        return restTemplate;
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}
