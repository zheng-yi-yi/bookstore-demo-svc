package com.bookstore.springboot.dto;

import lombok.Data;

@Data
public class UpdateBookDto {
    private String title;
    private String author;
    private Double price;
}
