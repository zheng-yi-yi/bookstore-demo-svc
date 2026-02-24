package com.bookstore.springboot.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class CurrentUser implements ICurrentUser {

    @Override
    public Optional<UUID> getId() {
        return getAppUserDetails().map(AppUserDetails::getId);
    }

    @Override
    public String getUserName() {
        return getAppUserDetails().map(AppUserDetails::getUsername).orElse(null);
    }

    @Override
    public String getEmail() {
        return getAppUserDetails().map(AppUserDetails::getEmail).orElse(null);
    }

    @Override
    public Collection<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return Collections.emptyList();
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }

    @Override
    public boolean isGranted(String permissionName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(permissionName));
    }

    private Optional<AppUserDetails> getAppUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof AppUserDetails userDetails) {
            return Optional.of(userDetails);
        }
        return Optional.empty();
    }
}
