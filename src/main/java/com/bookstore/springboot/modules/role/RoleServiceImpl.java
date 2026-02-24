package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.modules.role.dto.*;
import com.bookstore.springboot.core.service.CrudAppService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class RoleServiceImpl 
    extends CrudAppService<Role, RoleDto, UUID, RoleGetListInput, CreateRoleDto, UpdateRoleDto> 
    implements RoleService {
}
