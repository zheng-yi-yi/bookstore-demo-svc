package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.core.base.controller.CrudController;
import com.bookstore.springboot.modules.role.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/identity/roles")
public class RoleController extends CrudController<RoleDto, UUID, RoleGetListInput, CreateRoleDto, UpdateRoleDto> {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }
}

