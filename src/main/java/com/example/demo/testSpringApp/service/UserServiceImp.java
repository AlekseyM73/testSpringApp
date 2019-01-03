package com.example.demo.testSpringApp.service;
import com.example.demo.testSpringApp.model.User;
import com.example.demo.testSpringApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @Override
    public void addUser(User user) {
        userRepo.saveAndFlush(user);
    }
}
