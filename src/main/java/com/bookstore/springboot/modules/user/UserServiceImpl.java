package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.modules.user.dto.*;
import com.bookstore.springboot.core.service.CrudAppService;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserServiceImpl 
    extends CrudAppService<User, UserDto, UUID, UserGetListInput, CreateUserDto, UpdateUserDto> 
    implements UserService {
}
