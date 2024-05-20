package com.sammygojs.springbootlogindocker.repository;

import com.sammygojs.springbootlogindocker.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
}