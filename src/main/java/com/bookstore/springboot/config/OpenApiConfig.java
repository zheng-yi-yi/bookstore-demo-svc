package com.bookstore.springboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
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

    /**
     * 全局 OpenAPI 自定义器
     * 自动优化 OperationId，使其符合前端语义化调用习惯 (e.g., getBook, getBookList)
     */
    @Bean
    public GlobalOpenApiCustomizer globalOpenApiCustomizer() {
        return openApi -> {
            if (openApi.getPaths() == null) {
                return;
            }

            openApi.getPaths().forEach((path, pathItem) -> {
                pathItem.readOperations().forEach(operation -> {
                    if (operation.getTags() == null || operation.getTags().isEmpty()) {
                        return;
                    }

                    // 1. 规范化实体名称并修改 Tag (如 "book-controller" -> "BookService")
                    String tag = operation.getTags().get(0);
                    String rawEntityName = tag.split("-")[0];
                    String entityName = rawEntityName.substring(0, 1).toUpperCase() + rawEntityName.substring(1);
                    
                    // 将 Tag 修改为 Service 后缀，这会直接影响前端生成的文件名和类名
                    String serviceName = entityName + "Service";
                    operation.setTags(java.util.List.of(serviceName));

                    // 2. 识别并转换方法名 (优化为 getBook, getBookList, getUserByUsername 等)
                    String opId = operation.getOperationId();
                    if (opId == null) return;
                    
                    String lowOpId = opId.toLowerCase();
                    String action;
                    
                    if (lowOpId.equals("get")) action = "get" + entityName;
                    else if (lowOpId.equals("getlist")) action = "get" + entityName + "List";
                    else if (lowOpId.equals("create")) action = "create" + entityName;
                    else if (lowOpId.equals("update")) action = "update" + entityName;
                    else if (lowOpId.equals("delete")) action = "delete" + entityName;
                    else {
                        // 特殊方法处理: 尝试在谓词(get/create/etc)后插入实体名
                        if (opId.startsWith("get")) {
                            action = "get" + entityName + opId.substring(3);
                        } else if (opId.startsWith("create")) {
                            action = "create" + entityName + opId.substring(6);
                        } else if (opId.startsWith("update")) {
                            action = "update" + entityName + opId.substring(6);
                        } else if (opId.startsWith("delete")) {
                            action = "delete" + entityName + opId.substring(6);
                        } else {
                            action = opId + entityName;
                        }
                    }

                    operation.setOperationId(action);
                });
            });
        };
    }
}
