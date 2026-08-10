package com.realestate.user_auth_service.controller;

import com.realestate.user_auth_service.model.User;
import com.realestate.user_auth_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    // පරිශීලකයෙකු ලියාපදිංචි කිරීමේ Endpoint එක (/auth/register)
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    // පද්ධතියට ඇතුළුවීමේ Endpoint එක (/auth/login)
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        boolean isAuthenticated = userService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("පද්ධතියට සාර්ථකව ඇතුළු විය!");
        } else {
            return ResponseEntity.status(401).body("වැරදි ඊමේල් ලිපිනයක් හෝ මුරපදයකි!");
        }
    }
}