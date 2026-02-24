package com.bookstore.springboot.modules.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SendPasswordResetCodeDto {
    @NotBlank
    @Email
    private String email;
}
