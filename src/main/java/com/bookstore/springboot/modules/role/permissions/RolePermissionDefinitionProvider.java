package com.bookstore.springboot.modules.role.permissions;

import com.bookstore.springboot.core.infrastructure.permission.IPermissionDefinitionContext;
import com.bookstore.springboot.core.infrastructure.permission.IPermissionDefinitionProvider;
import com.bookstore.springboot.core.infrastructure.permission.PermissionDefinition;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionDefinitionProvider implements IPermissionDefinitionProvider {
    @Override
    public void define(IPermissionDefinitionContext context) {
        PermissionDefinition systemGroup = context.getPermissionOrNull(RolePermissions.GroupName);
        if (systemGroup == null) {
            systemGroup = context.addGroup(RolePermissions.GroupName, "System");
        }
        
        PermissionDefinition roles = systemGroup.addChild(RolePermissions.Roles, "Roles Management");
        roles.addChild(RolePermissions.Roles_Create, "Create");
        roles.addChild(RolePermissions.Roles_Edit, "Edit");
        roles.addChild(RolePermissions.Roles_Delete, "Delete");
    }
}

