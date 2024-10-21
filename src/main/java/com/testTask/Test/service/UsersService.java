package com.testTask.Test.service;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testTask.Test.models.User;
import com.testTask.Test.models.Users;
import com.testTask.Test.repository.interfaces.iUserRepository;

import java.io.InputStream;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private iUserRepository userRepository;

    public void parseXml(InputStream inputStream) throws Exception {
        JAXBContext jaxbContext = JAXBContext.newInstance(Users.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        Users users = (Users) unmarshaller.unmarshal(inputStream);
        for (User user : users.getUserList()) {
            userRepository.save(user);
        }
    }
    public List<User> getAll() {
        return userRepository.findAll(); 
    }
}
