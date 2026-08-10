package com.realestate.user_auth_service.service;

import com.realestate.user_auth_service.model.User;
import com.realestate.user_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 1. පරිශීලකයෙකු ලියාපදිංචි කිරීම (Register)
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    // 2. ලොගින් වීම (Login)
    public User loginUser(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // 3. සියලුම Users ලැයිස්තුව ලබාගැනීම (Get All)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 4. ID එක මඟින් User කෙනෙකු ලබාගැනීම (Get By ID)
    public User getUserById(String id) {
        Optional<User> userOpt = userRepository.findById(id);
        return userOpt.orElse(null);
    }

    // 5. User කෙනෙකුගේ තොරතුරු යාවත්කාලීන කිරීම (Update)
    public User updateUser(String id, User userDetails) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User existingUser = userOpt.get();
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setEmail(userDetails.getEmail());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setRole(userDetails.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    // 6. User කෙනෙකු ඉවත් කිරීම (Delete)
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}