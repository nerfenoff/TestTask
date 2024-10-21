package com.testTask.Test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testTask.Test.models.AuthUser;
import com.testTask.Test.repository.AuthUserRepository;

@RestController
public class AuthController {

    @Autowired
    private AuthUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello, authenticated user!";
  
    }
    @PostMapping("/api/v1/register")
    public AuthUser registerUser(@RequestBody AuthUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хэшируем пароль
        return userRepository.save(user);
    }
    @PostMapping("/register")
    public AuthUser registerUser2(@RequestBody AuthUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Хэшируем пароль
        return userRepository.save(user);
    }
}
