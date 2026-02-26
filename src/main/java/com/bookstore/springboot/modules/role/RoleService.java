package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.modules.role.dto.*;
import com.bookstore.springboot.core.base.service.ICrudAppService;
import java.util.List;
import java.util.UUID;

public interface RoleService extends ICrudAppService<RoleDto, UUID, RoleGetListInput, CreateRoleDto, UpdateRoleDto> {
    List<RoleDto> getAll();
}

