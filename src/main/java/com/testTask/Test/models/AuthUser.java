package com.testTask.Test.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "AuthUsers")
public class AuthUser {
    @Id
    private String id;
    private String username;
    private String password;
    private String role;
}
