package com.testTask.Test.repository.interfaces;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.testTask.Test.models.User;

public interface iUserRepository extends MongoRepository<User,String> {

}