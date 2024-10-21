package com.testTask.Test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.testTask.Test.models.AuthUser;
import java.util.Optional;

public interface  AuthUserRepository extends MongoRepository<AuthUser, String> {
    Optional<AuthUser> findByUsername(String username);
}
