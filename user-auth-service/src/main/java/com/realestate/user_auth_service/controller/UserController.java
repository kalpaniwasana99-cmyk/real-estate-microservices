package com.realestate.user_auth_service.controller;

import com.realestate.user_auth_service.model.User;
import com.realestate.user_auth_service.service.UserService;
import com.realestate.user_auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    // --- CRUD Endpoints (Assignment අවශ්‍යතාවය සඳහා) ---

    // 1. සියලුම Users ලැයිස්තුව ලබාගැනීම (GET)
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 2. ID එක මඟින් User කෙනෙකු ලබාගැනීම (GET)
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(404).body("පරිශීලකයා හමු නොවීය!");
        }
    }

    // 3. User කෙනෙකුගේ තොරතුරු යාවත්කාලීන කිරීම (PUT)
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User userDetails) {
        return ResponseEntity.ok(userService.updateUser(id, userDetails));
    }

    // 4. User කෙනෙකු ඉවත් කිරීම (DELETE) - මෙන්න නම නිවැරදි කළා
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("පරිශීලකයා සාර්ථකව ඉවත් කරන ලදී.");
    }
}