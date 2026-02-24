package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.modules.user.dto.*;
import com.bookstore.springboot.core.service.ICrudAppService;
import java.util.UUID;

public interface UserService extends ICrudAppService<UserDto, UUID, UserGetListInput, CreateUserDto, UpdateUserDto> {
}
