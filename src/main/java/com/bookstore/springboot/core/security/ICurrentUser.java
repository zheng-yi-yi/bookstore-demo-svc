package com.bookstore.springboot.core.security;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * ABP-style interface to access current user information.
 */
public interface ICurrentUser {
    Optional<UUID> getId();
    
    String getUserName();
    
    String getEmail();
    
    Collection<String> getRoles();
    
    boolean isAuthenticated();
    
    boolean isGranted(String permissionName);
}
