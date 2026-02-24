package com.bookstore.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookstoreDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreDemoApplication.class, args);
	}
}
