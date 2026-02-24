package com.bookstore.springboot.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserFriendlyException.class)
    public ResponseEntity<RemoteServiceErrorResponse> handleUserFriendlyException(UserFriendlyException ex) {
        RemoteServiceErrorInfo error = RemoteServiceErrorInfo.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .details(ex.getDetails())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RemoteServiceErrorResponse(error));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RemoteServiceErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        RemoteServiceErrorInfo error = RemoteServiceErrorInfo.builder()
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new RemoteServiceErrorResponse(error));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RemoteServiceErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationErrorInfo> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationErrorInfo(fieldError.getDefaultMessage(), Collections.singletonList(fieldError.getField())))
                .collect(Collectors.toList());

        RemoteServiceErrorInfo error = RemoteServiceErrorInfo.builder()
                .message("Your request is not valid!")
                .details("Please check validation errors for details.")
                .validationErrors(validationErrors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RemoteServiceErrorResponse(error));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RemoteServiceErrorResponse> handleGeneralException(Exception ex) {
        RemoteServiceErrorInfo error = RemoteServiceErrorInfo.builder()
                .message("An internal error occurred during your request!")
                .details(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RemoteServiceErrorResponse(error));
    }
}
