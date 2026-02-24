package com.bookstore.springboot.modules.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateUserDto {
    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    private String name;

    private String surname;

    private Set<String> roles;
}
