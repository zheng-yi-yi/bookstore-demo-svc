package com.bookstore.springboot.modules.account;

import com.bookstore.springboot.modules.account.dto.*;
import com.bookstore.springboot.modules.user.UserService;
import com.bookstore.springboot.modules.user.dto.CreateUserDto;
import com.bookstore.springboot.modules.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserService userService;

    @Override
    public UserDto register(RegisterDto input) {
        CreateUserDto createDto = CreateUserDto.builder()
                .username(input.getUsername())
                .email(input.getEmail())
                .password(input.getPassword())
                .name(input.getName())
                .surname(input.getSurname())
                .build();
        return userService.create(createDto);
    }

    @Override
    public void sendPasswordResetCode(SendPasswordResetCodeDto input) {
        // Mock sending email
        System.out.println("Sending password reset code to: " + input.getEmail());
    }

    @Override
    public boolean verifyPasswordResetToken(VerifyPasswordResetTokenDto input) {
        // Mock verification
        return "123456".equals(input.getToken());
    }

    @Override
    public void resetPassword(ResetPasswordDto input) {
        // Mock reset
        System.out.println("Resetting password for: " + input.getEmail());
    }
}
