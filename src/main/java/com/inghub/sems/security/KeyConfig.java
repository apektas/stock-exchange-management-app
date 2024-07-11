package com.inghub.sems.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class KeyConfig {

    @Value("${stock.app.admin-password}")
    String adminPassword;

    @Value("${stock.app.admin-user}")
    String adminUser;

    @Value("${stock.app.jwtSecret}")
    String jwtSecret;

    @Bean
    public SecretKey secretKey() {
        return new SecretKeySpec(
                jwtSecret.getBytes(StandardCharsets.UTF_8),
                "HMACSHA256"
        );
    }

    @Bean
    public String adminPassword() {
        return adminPassword;
    }

    @Bean
    public String adminUser() {
        return adminUser;
    }
}
