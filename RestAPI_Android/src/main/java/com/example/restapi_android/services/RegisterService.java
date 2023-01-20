package com.example.restapi_android.services;

import com.example.restapi_android.dto.LoginResponseDto;
import com.example.restapi_android.dto.RegisterRequestDto;
import com.example.restapi_android.dto.RegisterResponseDto;
import com.example.restapi_android.http_reponses.HttpResponses;
import com.example.restapi_android.model.User;
import com.example.restapi_android.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository repository;
    private final HttpResponses responses;

    public RegisterService(UserRepository repository, HttpResponses responses) {
        this.repository = repository;
        this.responses = responses;
    }



    public User getUserByEmail(String email){
        return repository.findUserByEmail(email).orElse(null);
    }
    public User getUserByCode(String code) {return repository.findUserByCode(code).orElse(null);}
    public User getUserByToken(String token) {
        return repository.findUserByToken(token).orElse(null);
    }
    public ResponseEntity<RegisterResponseDto> register(RegisterRequestDto dto){
        User user = getUserByEmail(dto.getEmail());
        if (user != null){
            return responses.badRequest();
        }else{
            User newUser = new User();
            newUser.setEmail(dto.getEmail());
            newUser.setPassword(dto.getPassword());
            repository.save(newUser);
            RegisterResponseDto responseDto = new RegisterResponseDto();
            return ResponseEntity.ok(responseDto);
        }
    }

    public User saveUser(User user){
        return repository.save(user);
    }


}
