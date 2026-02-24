package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.core.controller.CrudController;
import com.bookstore.springboot.modules.user.dto.*;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController extends CrudController<UserDto, UUID, UserGetListInput, CreateUserDto, UpdateUserDto> {
}
