package com.realestate.user_auth_service.controller;

import com.realestate.user_auth_service.model.User;
import com.realestate.user_auth_service.service.UserService;
import com.realestate.user_auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        User loggedUser = userService.loginUser(user.getEmail(), user.getPassword());
        
        if (loggedUser != null) {
            // Login සාර්ථක නම් JWT Token එක සාදා ගනිමු
            String token = jwtUtil.generateToken(loggedUser.getEmail(), loggedUser.getRole());

            // Token එක සහ පණිවිඩය එකට යැවීම සඳහා Response එකක් සාදමු
            Map<String, Object> response = new HashMap<>();
            response.put("message", "පද්ධතියට සාර්ථකව ඇතුළු විය!");
            response.put("token", token);
            response.put("user", loggedUser);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("ඊමේල් ලිපිනය හෝ මුරපදය වැරදියි!");
        }
    }
}