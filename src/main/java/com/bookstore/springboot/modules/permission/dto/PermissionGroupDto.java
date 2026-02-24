package com.bookstore.springboot.modules.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionGroupDto {
    private String name;
    private String displayName;
    private List<PermissionDto> permissions;
}
