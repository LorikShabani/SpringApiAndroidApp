package com.example.restapi_android.services;

import com.example.restapi_android.dto.CodeRequestDto;
import com.example.restapi_android.dto.LoginAttemptResponse;
import com.example.restapi_android.dto.LoginRequestDto;
import com.example.restapi_android.dto.LoginResponseDto;
import com.example.restapi_android.http_reponses.HttpResponses;
import com.example.restapi_android.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    private final RegisterService registerService;
    private final MailService mailService;
    private final HttpResponses responses;

    public AuthService(RegisterService registerService, MailService mailService, HttpResponses responses) {
        this.registerService = registerService;
        this.mailService = mailService;
        this.responses = responses;
    }

    private String getLoginCode(User user) {
        return String.format("Login Code: %s ", user.getCode());
    }

    public ResponseEntity<LoginAttemptResponse> loginAttempt(LoginRequestDto dto) {
        User user = registerService.getUserByEmail(dto.getEmail());
        if (user == null) {
            return responses.badRequest();
        } else if (user.getPassword().equals(dto.getPassword())) {
            user.setCode(responses.generateCode());
            registerService.saveUser(user);
            mailService.sendEmail(
                    user.getEmail(),
                    "Login Code",
                    getLoginCode(user));
            LoginAttemptResponse responseDto = new LoginAttemptResponse();
            responseDto.setCode(user.getCode());
            return ResponseEntity.ok(responseDto);
        }
        return responses.badRequest();
    }

    public ResponseEntity<LoginResponseDto> login(CodeRequestDto dto) {
        User user = registerService.getUserByCode(dto.getCode());
            //Logged in successfully
            if (user.getCode().equals(dto.getCode())) {
                user.setCode(null);
                user.setToken(UUID.randomUUID().toString());
                registerService.saveUser(user);
                LoginResponseDto responseDto = new LoginResponseDto();
                responseDto.setToken(user.getToken());
                return ResponseEntity.ok(responseDto);
            }
            return responses.badRequest();
    }

    public ResponseEntity<Void> logout(String token) {
        User user = registerService.getUserByToken(token);
        if (user != null) {
            user.setToken(null);
            registerService.saveUser(user);
            return ResponseEntity.ok().build();
        }
        return responses.badRequest();
    }
}
