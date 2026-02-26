package com.bookstore.springboot.core.seeding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 实现了 Spring Boot 的 CommandLineRunner 接口。
 * 当应用程序启动完成时，它会自动调用 DataSeeder.seed()，触发整个种子数据初始化流程。
 */
@Component
public class InitialDataSeeder implements CommandLineRunner {

    @Autowired
    private DataSeeder dataSeeder;

    @Override
    public void run(String... args) throws Exception {
        DataSeedContext context = new DataSeedContext();
        dataSeeder.seed(context);
    }
}

