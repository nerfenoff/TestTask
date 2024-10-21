package com.testTask.Test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    private UsersService userService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<User>>> getAllUser(){
        return userService.getAll()
                .thenApply(ResponseEntity::ok);
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Optional<User>>> getAllUser(@PathVariable String id){
        return userService.findUser(id)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> uploadXml(@RequestBody String xmlString) {
        return userService.parseXml(xmlString).thenApply(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> updateUser(@PathVariable String id, @RequestBody String entity) {
        return userService.updateUser(id, entity).thenApply(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteUser(@PathVariable String id){
        return userService.deleteUser(id)
                .thenApply(ResponseEntity::ok);
    }
}
