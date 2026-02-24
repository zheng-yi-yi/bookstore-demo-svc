package com.bookstore.springboot.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RemoteServiceErrorInfo {
    private String code;
    private String message;
    private String details;
    private List<ValidationErrorInfo> validationErrors;
}
