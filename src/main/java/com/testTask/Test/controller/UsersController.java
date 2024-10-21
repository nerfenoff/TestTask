package com.testTask.Test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testTask.Test.models.User;
import com.testTask.Test.service.UsersService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
    // @Autowired
    // private userService service;
    @Autowired
    private UsersService userService;

    @GetMapping
    public List<User> getAllUser(){
        return userService.getAll();
    }
    @PostMapping("/addUsers")
    public ResponseEntity<String> uploadXml(@RequestBody String xmlString) {
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));

            userService.parseXml(inputStream);
            return ResponseEntity.ok("File processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        }
    }
}
