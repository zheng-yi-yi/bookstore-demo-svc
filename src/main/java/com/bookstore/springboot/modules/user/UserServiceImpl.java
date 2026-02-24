package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.modules.user.dto.*;
import com.bookstore.springboot.core.service.CrudAppService;
import com.bookstore.springboot.modules.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

import com.bookstore.springboot.modules.user.dto.*;
import com.bookstore.springboot.core.service.CrudAppService;
import com.bookstore.springboot.modules.role.Role;
import com.bookstore.springboot.modules.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl 
    extends CrudAppService<User, UserDto, UUID, UserGetListInput, CreateUserDto, UpdateUserDto> 
    implements UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDto create(CreateUserDto input) {
        User entity = mapper.toEntity(input);
        entity.setPassword(passwordEncoder.encode(input.getPassword()));
        if (input.getRoles() != null && !input.getRoles().isEmpty()) {
            entity.setRoles(roleRepository.findByNameIn(input.getRoles()));
        }
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    @Transactional
    public UserDto update(UUID id, UpdateUserDto input) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        mapper.updateEntityFromDto(input, entity);
        if (input.getRoles() != null) {
            entity.setRoles(roleRepository.findByNameIn(input.getRoles()));
        }
        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public Set<String> getRoles(UUID id) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return entity.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void updateRoles(UUID id, Set<String> roleNames) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        entity.setRoles(roleRepository.findByNameIn(roleNames));
        repository.save(entity);
    }

    @Override
    public UserDto getByUsername(String username) {
        User entity = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(entity);
    }

    @Override
    public UserDto getByEmail(String email) {
        User entity = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapper.toDto(entity);
    }

    @Override
    public List<String> getAssignableRoles() {
        return roleRepository.findAll().stream().map(Role::getName).collect(Collectors.toList());
    }
}
