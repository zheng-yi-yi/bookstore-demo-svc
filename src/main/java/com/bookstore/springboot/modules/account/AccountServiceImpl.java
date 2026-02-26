package com.bookstore.springboot.modules.account;

import com.bookstore.springboot.core.infrastructure.security.JwtProperties;
import com.bookstore.springboot.core.infrastructure.security.TokenProvider;
import com.bookstore.springboot.modules.account.dto.*;
import com.bookstore.springboot.modules.permission.PermissionGrant;
import com.bookstore.springboot.modules.permission.PermissionGrantRepository;
import com.bookstore.springboot.modules.user.UserService;
import com.bookstore.springboot.modules.user.dto.CreateUserDto;
import com.bookstore.springboot.modules.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private PermissionGrantRepository permissionGrantRepository;

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

        UserDto userDto = userService.getByUsername(input.getUsername());

        // Fetch permissions for all roles
        List<PermissionGrant> roleGrants = permissionGrantRepository.findByProviderNameAndProviderKeyIn(
                "R", userDto.getRoles());

        // Fetch permissions directly for user
        List<PermissionGrant> userGrants = permissionGrantRepository.findByProviderNameAndProviderKey(
                "U", userDto.getId().toString());

        Set<String> permissions = new HashSet<>();
        permissions.addAll(roleGrants.stream().map(PermissionGrant::getName).collect(Collectors.toSet()));
        permissions.addAll(userGrants.stream().map(PermissionGrant::getName).collect(Collectors.toSet()));

        return LoginResultDto.builder()
                .accessToken(jwt)
                .expiresInSeconds(jwtProperties.getExpireTime())
                .permissions(permissions)
                .userInfo(LoginResultDto.UserInfo.builder()
                        .id(userDto.getId())
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .name(userDto.getName())
                        .surname(userDto.getSurname())
                        .roles(userDto.getRoles())
                        .build())
                .build();
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

