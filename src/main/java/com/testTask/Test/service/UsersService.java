package com.testTask.Test.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

import com.testTask.Test.models.User;
import com.testTask.Test.models.Users;
import com.testTask.Test.repository.interfaces.iUserRepository;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UsersService {
    @Autowired
    private iUserRepository userRepository;

    @Async
    public CompletableFuture<String> parseXml(String xmlString) {
        Users users;
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    
            users = (Users) unmarshaller.unmarshal(inputStream);
            for (User user : users.getUserList()) {
                userRepository.save(user);
            }
        } catch(Exception e) {
            return CompletableFuture.completedFuture("Error: " + e.getMessage()); 
        }
        

        return CompletableFuture.completedFuture("Processed: "+users.getUserList().size());
    }
    @Async
    public CompletableFuture<List<User>> getAll() {
        return CompletableFuture.completedFuture(userRepository.findAll()); 
    }
    @Async
    public CompletableFuture<Optional<User>> findUser(String id) {
        return CompletableFuture.completedFuture(userRepository.findById(id)); 
    }
    @Async
    public CompletableFuture<String> updateUser(String id, String xmlString) {
        if(!userRepository.findById(id).isPresent()) {
            return CompletableFuture.completedFuture("User not found"); 
        }
        try {
            InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(StandardCharsets.UTF_8));
            JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            User user = (User) unmarshaller.unmarshal(inputStream);
            user.setId(id);
            userRepository.save(user);
        } catch(Exception e) {
            return CompletableFuture.completedFuture(e.getMessage()); 
        }
        
        return CompletableFuture.completedFuture("Updated"); 
    }
    @Async
    public CompletableFuture<String> deleteUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch(Exception e) {
            return CompletableFuture.completedFuture("Error: "+e.getMessage()); 
        }
        
        return CompletableFuture.completedFuture("Done"); 
    }
}
