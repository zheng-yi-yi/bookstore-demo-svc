package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.modules.user.dto.*;
import com.bookstore.springboot.core.service.CrudAppService;
import com.bookstore.springboot.modules.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class UserServiceImpl 
    extends CrudAppService<User, UserDto, UUID, UserGetListInput, CreateUserDto, UpdateUserDto> 
    implements UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDto create(CreateUserDto input) {
        User entity = mapper.toEntity(input);
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
}
