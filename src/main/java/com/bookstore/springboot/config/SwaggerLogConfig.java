package com.bookstore.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * 启动完成后在日志中打印 Swagger UI 地址
 */
@Slf4j
@Configuration
public class SwaggerLogConfig {

    @Value("${server.port:8080}")
    private String port;

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${springdoc.swagger-ui.path:/swagger-ui/index.html}")
    private String swaggerPath;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        String formattedContextPath = contextPath;
        if (formattedContextPath != null && !formattedContextPath.isEmpty() && !formattedContextPath.startsWith("/")) {
            formattedContextPath = "/" + formattedContextPath;
        }

        String url = "http://localhost:" + port + (formattedContextPath == null ? "" : formattedContextPath) + swaggerPath;

        log.info("Access Swagger UI at: {}", url);
    }
}
