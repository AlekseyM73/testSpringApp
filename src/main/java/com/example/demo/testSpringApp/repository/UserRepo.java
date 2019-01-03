package com.example.demo.testSpringApp.repository;
import com.example.demo.testSpringApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
		
}
