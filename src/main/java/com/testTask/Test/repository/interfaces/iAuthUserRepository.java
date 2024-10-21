package com.testTask.Test.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.testTask.Test.models.AuthUser;

public interface iAuthUserRepository extends MongoRepository<AuthUser, String> {
    AuthUser findByUsername(String username);
}