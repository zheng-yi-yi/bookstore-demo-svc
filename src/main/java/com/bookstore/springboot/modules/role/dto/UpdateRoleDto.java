package com.bookstore.springboot.modules.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UpdateRoleDto {
    @NotBlank
    private String name;

    private String description;
}
