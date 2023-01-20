package com.example.restapi_android.http_reponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class HttpResponses {
    public ResponseEntity badRequest(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public ResponseEntity success(String message){
        return ResponseEntity.ok(message);
    }

    public String generateCode(){
        Random random = new Random();
        return String.format("%04d", random.nextLong(0, 9999));
    }

}
