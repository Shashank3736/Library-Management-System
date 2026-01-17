package com.tcs.Library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tcs.Library.dto.LoginRequest;
import com.tcs.Library.dto.SignUpResponse;
import com.tcs.Library.dto.UserRegRequest;
import com.tcs.Library.service.AuthService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {
    @Autowired
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return ResponseEntity.ok(authService.login(loginRequest));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.badRequest().body("Incorrect Credentials");
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> Signup(@RequestBody UserRegRequest signuprequest) {
        return ResponseEntity.ok(authService.signup(signuprequest));
    }

    @PostMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("Test");
    }

}
