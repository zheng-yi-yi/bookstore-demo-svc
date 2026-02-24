package com.bookstore.springboot.core.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    /**
     * Secret key for signing JWTs. 
     * In production, this MUST be overwritten by an environment variable (APP_JWT_SECRET).
     */
    private String secret;
    
    /**
     * Token expiration time in seconds.
     */
    private long expireTime = 3600; // Default 1 hour
}
