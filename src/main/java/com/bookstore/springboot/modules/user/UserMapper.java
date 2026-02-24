package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.core.mapper.BaseMapper;
import com.bookstore.springboot.core.mapper.IgnoreAuditedProperties;
import com.bookstore.springboot.modules.user.dto.UserDto;
import com.bookstore.springboot.modules.user.dto.CreateUserDto;
import com.bookstore.springboot.modules.user.dto.UpdateUserDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<User, UserDto, CreateUserDto, UpdateUserDto> {
    
    @Override
    UserDto toDto(User user);

    @Override
    @IgnoreAuditedProperties
    @Mapping(target = "password", source = "password")
    User toEntity(CreateUserDto dto);

    @Override
    @IgnoreAuditedProperties
    @Mapping(target = "password", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateUserDto dto, @MappingTarget User entity);
}
