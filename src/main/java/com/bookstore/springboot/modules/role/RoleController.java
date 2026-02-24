package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.core.controller.CrudController;
import com.bookstore.springboot.modules.role.dto.*;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends CrudController<RoleDto, UUID, RoleGetListInput, CreateRoleDto, UpdateRoleDto> {
}
