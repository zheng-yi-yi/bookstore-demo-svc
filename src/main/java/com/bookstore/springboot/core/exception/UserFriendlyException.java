package com.bookstore.springboot.core.exception;

import lombok.Getter;

@Getter
public class UserFriendlyException extends RuntimeException {
    private final String code;
    private final String details;

    public UserFriendlyException(String message) {
        this(message, null, null);
    }

    public UserFriendlyException(String message, String code) {
        this(message, code, null);
    }

    public UserFriendlyException(String message, String code, String details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public UserFriendlyException(String message, Throwable cause) {
        super(message, cause);
        this.code = null;
        this.details = null;
    }
}
