package com.bookstore.springboot.modules.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;
}
