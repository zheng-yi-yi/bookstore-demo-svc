package com.bookstore.springboot.modules.book.dto;

import lombok.Data;

@Data
public class UpdateBookDto {
    private String title;
    private String author;
    private Double price;
}
