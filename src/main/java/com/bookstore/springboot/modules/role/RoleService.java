package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.modules.role.dto.*;
import com.bookstore.springboot.core.service.ICrudAppService;
import java.util.UUID;

public interface RoleService extends ICrudAppService<RoleDto, UUID, RoleGetListInput, CreateRoleDto, UpdateRoleDto> {
}
