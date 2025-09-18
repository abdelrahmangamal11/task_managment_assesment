package com.assesment.task_project.services;

import com.assesment.task_project.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AuthService {
    User register(User user);
    UserDetails authenticate (String email, String Password);
    String generateToken(UserDetails userDetails);
    UserDetails validateToken(String token);
     List<User> findusers();
}
