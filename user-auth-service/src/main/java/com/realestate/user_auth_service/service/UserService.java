package com.realestate.user_auth_service.service;

import com.realestate.user_auth_service.model.User;
import com.realestate.user_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // පරිශීලකයෙකු ලියාපදිංචි කිරීම (Register)
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // පද්ධතියට ඇතුළුවීම තහවුරු කිරීම (Login authentication)
    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // සරල මුරපද පරීක්ෂාව
            return user.getPassword().equals(password);
        }
        return false;
    }
}