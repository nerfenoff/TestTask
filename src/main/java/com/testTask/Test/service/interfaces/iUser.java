package com.testTask.Test.service.interfaces;

import com.testTask.Test.models.User;
import java.util.List;


public interface iUser {
    public List<User> getAll();
    public String addUsers(List<User> users);
    public User updateUser(User user);
    public String removeUser(String user);
}
