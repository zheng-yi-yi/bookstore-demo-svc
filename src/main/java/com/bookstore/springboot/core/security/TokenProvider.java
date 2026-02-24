package com.bookstore.springboot.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    @Autowired
    private JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecret());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + jwtProperties.getExpireTime() * 1000);

        var builder = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .expiration(validity);

        if (authentication.getPrincipal() instanceof AppUserDetails userDetails) {
            builder.claim("userId", userDetails.getId().toString());
            builder.claim("email", userDetails.getEmail());
        }

        return builder.compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .filter(auth -> !auth.trim().isEmpty())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String userIdStr = claims.get("userId", String.class);
        String email = claims.get("email", String.class);
        UUID userId = userIdStr != null ? UUID.fromString(userIdStr) : null;

        AppUserDetails principal = new AppUserDetails(userId, claims.getSubject(), "", email, authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
