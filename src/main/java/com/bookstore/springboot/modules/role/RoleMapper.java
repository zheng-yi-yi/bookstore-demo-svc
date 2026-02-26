package com.bookstore.springboot.modules.role;

import com.bookstore.springboot.core.base.mapper.BaseMapper;
import com.bookstore.springboot.core.base.mapper.IgnoreAuditedProperties;
import com.bookstore.springboot.modules.role.dto.RoleDto;
import com.bookstore.springboot.modules.role.dto.CreateRoleDto;
import com.bookstore.springboot.modules.role.dto.UpdateRoleDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMapper extends BaseMapper<Role, RoleDto, CreateRoleDto, UpdateRoleDto> {
    
    @Override
    RoleDto toDto(Role role);

    @Override
    @IgnoreAuditedProperties
    @Mapping(target = "staticRole", ignore = true)
    Role toEntity(CreateRoleDto dto);

    @Override
    @IgnoreAuditedProperties
    @Mapping(target = "staticRole", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateRoleDto dto, @MappingTarget Role entity);
}

