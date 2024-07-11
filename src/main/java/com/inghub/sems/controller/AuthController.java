package com.inghub.sems.controller;



import com.inghub.sems.model.AuthenticationRequest;
import com.inghub.sems.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpellCheckingInspection")
@RequiredArgsConstructor
@RestController
public class AuthController {

    final AuthenticationService authenticationService;

    @PostMapping("/api/v1/auth/token")
    public ResponseEntity<?> authenticate(@RequestParam(required = false) String username,
                                          @RequestParam(required = false) String password,
                                          @RequestParam(required = false) String refreshToken) {

        return ResponseEntity.ok().body(
                authenticationService.authenticate(
                        new AuthenticationRequest(username, password, refreshToken)
                )
        );
    }
}
