package com.example.restapi_android.controller;

import com.example.restapi_android.dto.*;
import com.example.restapi_android.services.AuthService;
import com.example.restapi_android.services.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UserController {
    private final RegisterService registerService;
    private final AuthService authService;

    public UserController(RegisterService registerService, AuthService authService) {
        this.registerService = registerService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody RegisterRequestDto dto) {
        return registerService.register(dto);
    }

    @PostMapping("/loginAttempt")
    public ResponseEntity<LoginAttemptResponse> loginAttempt(@RequestBody LoginRequestDto code) {
        return authService.loginAttempt(code);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody CodeRequestDto code) {
        return authService.login(code);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader(value="Authorization") String token) {
        return authService.logout(token);
    }

}
