package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.modules.role.dto.*;
import com.bookstore.springboot.core.service.CrudAppService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl 
    extends CrudAppService<Role, RoleDto, UUID, RoleGetListInput, CreateRoleDto, UpdateRoleDto> 
    implements RoleService {

    @Override
    public List<RoleDto> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
