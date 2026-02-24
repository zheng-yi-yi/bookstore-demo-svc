package com.bookstore.springboot.modules.account;

import com.bookstore.springboot.core.security.TokenProvider;
import com.bookstore.springboot.modules.account.dto.*;
import com.bookstore.springboot.modules.user.UserService;
import com.bookstore.springboot.modules.user.dto.CreateUserDto;
import com.bookstore.springboot.modules.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

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
    public LoginResultDto login(LoginDto input) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        return LoginResultDto.builder().accessToken(jwt).build();
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
