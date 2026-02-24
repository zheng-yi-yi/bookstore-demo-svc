package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.core.controller.CrudController;
import com.bookstore.springboot.modules.user.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/identity/users")
public class UserController extends CrudController<UserDto, UUID, UserGetListInput, CreateUserDto, UpdateUserDto> {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/roles")
    public Set<String> getRoles(@PathVariable UUID id) {
        return userService.getRoles(id);
    }

    @PutMapping("/{id}/roles")
    public void updateRoles(@PathVariable UUID id, @RequestBody Set<String> roleNames) {
        userService.updateRoles(id, roleNames);
    }

    @GetMapping("/by-username/{userName}")
    public UserDto getByUsername(@PathVariable String userName) {
        return userService.getByUsername(userName);
    }

    @GetMapping("/by-email/{email}")
    public UserDto getByEmail(@PathVariable String email) {
        return userService.getByEmail(email);
    }

    @GetMapping("/assignable-roles")
    public List<String> getAssignableRoles() {
        return userService.getAssignableRoles();
    }
}
