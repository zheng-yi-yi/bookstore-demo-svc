package com.bookstore.springboot.modules.book.permissions;

import com.bookstore.springboot.core.permission.IPermissionDefinitionContext;
import com.bookstore.springboot.core.permission.IPermissionDefinitionProvider;
import com.bookstore.springboot.core.permission.PermissionDefinition;
import org.springframework.stereotype.Component;

@Component
public class BookPermissionDefinitionProvider implements IPermissionDefinitionProvider {
    @Override
    public void define(IPermissionDefinitionContext context) {
        PermissionDefinition bookstoreGroup = context.addGroup(BookPermissions.GroupName, "Bookstore");
        
        PermissionDefinition books = bookstoreGroup.addChild(BookPermissions.Books, "Books Management");
        books.addChild(BookPermissions.Books_Create, "Create");
        books.addChild(BookPermissions.Books_Edit, "Edit");
        books.addChild(BookPermissions.Books_Delete, "Delete");
    }
}
