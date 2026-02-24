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
public class PermissionDto {
    private String name;
    private String displayName;
    private String parentName;
    private boolean isGranted;
    private List<String> allowedProviders;
}
