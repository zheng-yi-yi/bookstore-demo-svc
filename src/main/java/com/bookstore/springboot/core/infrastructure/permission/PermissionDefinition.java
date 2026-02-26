package com.bookstore.springboot.core.infrastructure.permission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PermissionDefinition {
    private String name;
    private String displayName;
    private String parentName;
    private List<PermissionDefinition> children = new ArrayList<>();

    public PermissionDefinition addChild(String name, String displayName) {
        PermissionDefinition child = PermissionDefinition.builder()
                .name(name)
                .displayName(displayName)
                .parentName(this.name)
                .children(new ArrayList<>())
                .build();
        this.children.add(child);
        return child;
    }
}

