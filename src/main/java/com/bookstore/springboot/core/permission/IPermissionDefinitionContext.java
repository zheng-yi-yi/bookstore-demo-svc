package com.bookstore.springboot.core.permission;

public interface IPermissionDefinitionContext {
    PermissionDefinition addGroup(String name, String displayName);
    PermissionDefinition getPermissionOrNull(String name);
}
