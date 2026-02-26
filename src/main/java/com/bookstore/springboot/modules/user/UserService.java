package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.modules.user.dto.*;
import com.bookstore.springboot.core.base.service.ICrudAppService;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface UserService extends ICrudAppService<UserDto, UUID, UserGetListInput, CreateUserDto, UpdateUserDto> {
    Set<String> getRoles(UUID id);
    void updateRoles(UUID id, Set<String> roleNames);
    UserDto getByUsername(String username);
    UserDto getByEmail(String email);
    List<String> getAssignableRoles();
}

