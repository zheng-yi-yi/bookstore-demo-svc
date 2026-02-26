package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.core.seeding.DataSeedContext;
import com.bookstore.springboot.core.seeding.IDataSeedContributor;
import com.bookstore.springboot.modules.role.Role;
import com.bookstore.springboot.modules.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Component
public class UserDataSeedContributor implements IDataSeedContributor {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void seed(DataSeedContext context) {
        if (roleRepository.count() == 0) {
            roleRepository.save(Role.builder()
                    .name("admin")
                    .description("Administrator role")
                    .staticRole(true)
                    .build());
        }

        if (userRepository.count() > 0) {
            return;
        }

        Role adminRole = roleRepository.findAll().stream()
                .filter(r -> r.getName().equals("admin"))
                .findFirst().orElse(null);

        userRepository.save(User.builder()
                .username("admin")
                .email("admin@bookstore.com")
                .password(passwordEncoder.encode("1q2w3E*"))
                .name("Admin")
                .surname("System")
                .roles(adminRole != null ? new HashSet<>(Collections.singletonList(adminRole)) : new HashSet<>())
                .build());

        userRepository.save(User.builder()
                .username("john.doe")
                .email("john.doe@example.com")
                .password(passwordEncoder.encode("1q2w3E*"))
                .name("John")
                .surname("Doe")
                .build());
    }
}

