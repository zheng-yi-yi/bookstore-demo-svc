package com.bookstore.springboot.core.infrastructure.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.util.*;

@Component
public class PermissionRegistry implements IPermissionDefinitionContext {
    private final Map<String, PermissionDefinition> permissions = new HashMap<>();
    private final List<PermissionDefinition> groups = new ArrayList<>();

    @Autowired(required = false)
    private List<IPermissionDefinitionProvider> providers = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (providers != null) {
            for (IPermissionDefinitionProvider provider : providers) {
                provider.define(this);
            }
        }
    }

    @Override
    public PermissionDefinition addGroup(String name, String displayName) {
        PermissionDefinition group = PermissionDefinition.builder()
                .name(name)
                .displayName(displayName)
                .children(new ArrayList<>())
                .build();
        groups.add(group);
        permissions.put(name, group);
        return group;
    }

    @Override
    public PermissionDefinition getPermissionOrNull(String name) {
        return permissions.get(name);
    }

    public List<PermissionDefinition> getGroups() {
        return groups;
    }

    public Map<String, PermissionDefinition> getAllPermissions() {
        return permissions;
    }
}

