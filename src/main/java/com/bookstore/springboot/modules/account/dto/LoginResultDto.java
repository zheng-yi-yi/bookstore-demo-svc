package com.bookstore.springboot.modules.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResultDto {
    private String accessToken;
    private long expiresInSeconds;
    private UserInfo userInfo;
    private Set<String> permissions;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfo {
        private UUID id;
        private String username;
        private String email;
        private String name;
        private String surname;
        private Set<String> roles;
    }
}
