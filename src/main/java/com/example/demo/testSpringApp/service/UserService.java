package com.example.demo.testSpringApp.service;

import com.example.demo.testSpringApp.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    void addUser(User user);
}
