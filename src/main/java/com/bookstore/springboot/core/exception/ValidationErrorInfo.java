package com.bookstore.springboot.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorInfo {
    private String message;
    private List<String> members;

    public ValidationErrorInfo(String message) {
        this.message = message;
    }
}
