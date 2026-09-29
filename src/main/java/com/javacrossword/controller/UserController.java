package com.javacrossword.controller;

import com.javacrossword.model.User;
import com.javacrossword.service.JwtService;
import com.javacrossword.service.TokenRevocationService;
import com.javacrossword.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final TokenRevocationService tokenRevocationService;
    private final JwtService jwtService;

    public UserController(
            UserService userService,
            TokenRevocationService tokenRevocationService,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.tokenRevocationService = tokenRevocationService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

   @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    User created = userService.createUser(user);
    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user
    ) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

   @PostMapping("/login")
public String login(@RequestBody User user) {

   return userService.login(
        user.getEmail(),
        user.getPassword()
    );
}

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String authHeader
    ) {

        String token = authHeader.substring(7);

        Date expiration =
                jwtService.extractExpiration(token);

        LocalDateTime expiresAt =
                expiration.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime();

        tokenRevocationService.revokeToken(
                token,
                expiresAt
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email) {
        return userService.createPasswordResetToken(email);
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestBody Map<String, String> data) {
        return userService.resetPassword(
                data.get("token"),
                data.get("password")
        );
    }
}