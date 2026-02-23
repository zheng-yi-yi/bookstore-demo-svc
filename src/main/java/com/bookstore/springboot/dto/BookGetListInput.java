package com.bookstore.springboot.dto;

import lombok.Data;

@Data
public class BookGetListInput {
    private String title;
    private String author;
}
