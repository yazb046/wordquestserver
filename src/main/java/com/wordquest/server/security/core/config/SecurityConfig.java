package com.wordquest.server.security.core.config;


import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class SecurityConfig {
    @Value("${jwt.signing.key}")
    private String signingKey;

    @Bean
    public SecretKey secretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(signingKey);
        if (keyBytes.length < 32) { // 32 bytes * 8 = 256 bits
            throw new IllegalArgumentException("The specified key byte array is too short for the HS256 algorithm. It must be at least 256 bits.");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
