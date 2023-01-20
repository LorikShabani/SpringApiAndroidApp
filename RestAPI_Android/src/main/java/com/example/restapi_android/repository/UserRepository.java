package com.example.restapi_android.repository;

import com.example.restapi_android.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByCode(String code);
    Optional<User> findUserByToken(String token);
}
