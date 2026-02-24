package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.core.mapper.BaseMapper;
import com.bookstore.springboot.core.mapper.IgnoreAuditedProperties;
import com.bookstore.springboot.modules.role.Role;
import com.bookstore.springboot.modules.user.dto.UserDto;
import com.bookstore.springboot.modules.user.dto.CreateUserDto;
import com.bookstore.springboot.modules.user.dto.UpdateUserDto;
import org.mapstruct.*;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<User, UserDto, CreateUserDto, UpdateUserDto> {
    
    @Override
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRolesToNames")
    UserDto toDto(User user);

    @Override
    @IgnoreAuditedProperties
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", ignore = true)
    User toEntity(CreateUserDto dto);

    @Override
    @IgnoreAuditedProperties
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateUserDto dto, @MappingTarget User entity);

    @Named("mapRolesToNames")
    default Set<String> mapRolesToNames(Set<Role> roles) {
        if (roles == null) return null;
        return roles.stream().map(Role::getName).collect(Collectors.toSet());
    }
}
