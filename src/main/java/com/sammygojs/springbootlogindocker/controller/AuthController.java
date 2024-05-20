package com.sammygojs.springbootlogindocker.controller;

import com.sammygojs.springbootlogindocker.model.User;
import com.sammygojs.springbootlogindocker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        System.out.println("Controller running");
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.loginUser(user.getEmail(), user.getPassword());
    }
}
