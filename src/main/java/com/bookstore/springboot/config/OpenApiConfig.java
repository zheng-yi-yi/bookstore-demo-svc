package com.bookstore.springboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI 配置类
 * 用于展示 Swagger 接口文档的详细信息
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bookstoreOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Bookstore Demo API")
                        .description("基于 Spring Boot 3.5.x 的图书商城演示项目接口文档")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://github.com/springdoc/springdoc-openapi")));
    }
}
