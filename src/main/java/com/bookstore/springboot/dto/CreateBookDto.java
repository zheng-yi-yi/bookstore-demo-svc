package com.bookstore.springboot.dto;

import lombok.Data;

@Data
public class CreateBookDto {
    private String title;
    private String author;
    private double price;
}
