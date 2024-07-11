package com.inghub.sems.service.impl;

import com.inghub.sems.exception.CustomAuthenticationException;
import com.inghub.sems.model.AuthenticationRequest;
import com.inghub.sems.model.AuthenticationResponse;
import com.inghub.sems.security.TokenProvider;
import com.inghub.sems.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@SuppressWarnings("SpellCheckingInspection")
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    final AuthenticationManager authenticationManager;
    final TokenProvider tokenProvider;

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {


        if (authenticationRequest.getRefreshToken() == null)
            return authenticateUserWithCredentials(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword());

        return refreshTokens(authenticationRequest.getRefreshToken());
    }


    private AuthenticationResponse refreshTokens(String refreshToken) {

        if (!tokenProvider.isRefreshTokenValid(refreshToken))
            throw new CustomAuthenticationException("Refresh token is invalid");

        if (tokenProvider.isTokenExpired(refreshToken))
            throw new CustomAuthenticationException("Refresh token is expired");

        String username = tokenProvider.extractUsername(refreshToken);

        return new AuthenticationResponse(
                tokenProvider.generateAccessToken(username),
                tokenProvider.generateRefreshToken(username)
        );
    }

    private AuthenticationResponse authenticateUserWithCredentials(String username, String password) {


        if (username == null || password == null)
            throw new CustomAuthenticationException("Username or password is invalid");

        Authentication authenticationResponse;
        try {
            authenticationResponse =
                    this.authenticationManager.authenticate(
                            UsernamePasswordAuthenticationToken.unauthenticated(username, password)
                    );
        } catch (AuthenticationException e) {
            throw new CustomAuthenticationException("Username or password is invalid");
        }

        return new AuthenticationResponse(
                tokenProvider.generateAccessToken(authenticationResponse),
                tokenProvider.generateRefreshToken(authenticationResponse)
        );
    }
}