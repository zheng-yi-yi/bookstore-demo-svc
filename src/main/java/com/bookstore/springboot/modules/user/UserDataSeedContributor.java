package com.bookstore.springboot.modules.user;

import com.bookstore.springboot.core.data.DataSeedContext;
import com.bookstore.springboot.core.data.IDataSeedContributor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class UserDataSeedContributor implements IDataSeedContributor {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void seed(DataSeedContext context) {
        if (userRepository.count() > 0) {
            return;
        }

        userRepository.save(User.builder()
                .username("admin")
                .email("admin@bookstore.com")
                .password("admin123")
                .name("Admin")
                .surname("System")
                .build());

        userRepository.save(User.builder()
                .username("john.doe")
                .email("john.doe@example.com")
                .password("password123")
                .name("John")
                .surname("Doe")
                .build());
    }
}
