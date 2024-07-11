package com.inghub.sems.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Component
public class TokenProvider {

    final SecretKey secretKey;

    public String generateAccessToken(String username) {

        return Jwts.builder()
                .subject(username)
                .claim("type", TokenType.ACCESS_TOKEN)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 604800000))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String generateRefreshToken(String username) {

        return Jwts.builder()
                .subject(username)
                .claim("type", TokenType.REFRESH_TOKEN)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 604800000))
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return generateAccessToken(user.getUsername());
    }

    public String generateRefreshToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return generateRefreshToken(user.getUsername());
    }

    public String extractTokenFromRequest(HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer "))
            return authorizationHeader.substring(7);

        return null;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return getClaims(token).getPayload();
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Jws<Claims> getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
    }

    public Boolean isAccessTokenValid(String token) {

        Claims claims;
        try {
            claims = extractAllClaims(token);
        } catch (Exception e) {
            return false;
        }

        return TokenType.ACCESS_TOKEN.name().equals(claims.get("type"));
    }

    public Boolean isRefreshTokenValid(String token) {

        Claims claims;
        try {
            claims = extractAllClaims(token);
        } catch (Exception e) {
            return false;
        }

        return TokenType.REFRESH_TOKEN.name().equals(claims.get("type"));
    }
}