package com.issac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: ywy
 * @date: 2020-05-13
 * @desc:
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    // 配置swagger2核心配置 docket
    // 地址 /swagger-ui.html
    //      /doc.html
    @Bean
    public Docket createRestApi() {
        // 指定api类型为 swagger2
        return new Docket(DocumentationType.SWAGGER_2)
                // 定义api文档汇总信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.issac.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档标题
                .title("ISSAC 接口平台api")
                .contact(new Contact("IssacYoung","issacyoung.cn","issacyoung@msn.cn"))
                .description("API 文档")
                .version("1.0.0")
                .termsOfServiceUrl("issacyoung.cn")
                .build();
    }

}
