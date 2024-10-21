package com.testTask.Test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testTask.Test.models.AuthUser;
import com.testTask.Test.repository.AuthUserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
public class AuthController {

    @Autowired
    private AuthUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Operation(summary = "Добавить пользователя в базовую авторизацию")
    @PostMapping("/register")
    public AuthUser registerUser2(@Parameter(description = "JSON пользователя для авторизации") @RequestBody AuthUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хэшируем пароль
        return userRepository.save(user);
    }
}
