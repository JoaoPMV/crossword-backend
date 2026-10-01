package com.javacrossword.service;

import com.javacrossword.model.PasswordResetToken;
import com.javacrossword.model.User;
import com.javacrossword.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            PasswordResetTokenService passwordResetTokenService,
            EmailService emailService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.passwordResetTokenService = passwordResetTokenService;
        this.emailService = emailService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());

        // Só troca a senha se uma nova foi enviada
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public String login(String email, String password) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required");
        }

        if (password == null || password.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        String stored = user.getPassword();
        boolean isHashed = stored != null && stored.startsWith("$2");

        boolean valid = isHashed
                ? passwordEncoder.matches(password, stored)
                : stored != null && stored.equals(password); // usuário antigo (texto puro)

        if (!valid) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        // Migração: converte a senha antiga para hash no primeiro login
        if (!isHashed) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }

        return jwtService.generateToken(user.getEmail(), user.getId());
    }

    public String createPasswordResetToken(String email) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found"
            );
        }

        PasswordResetToken resetToken =
                passwordResetTokenService.createToken(user);

        try {
            emailService.sendPasswordResetEmail(
                    user.getEmail(),
                    resetToken.getToken()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail", e);
        }

        return resetToken.getToken();
    }

    public String resetPassword(String token, String password) {

        if (password.length() < 8) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password must be at least 8 characters"
            );
        }

        PasswordResetToken resetToken =
                passwordResetTokenService.findByToken(token);

        if (resetToken == null) {
            return "Invalid Token.";
        }

        if (resetToken.getExpiration().isBefore(LocalDateTime.now())) {
            return "Expired Token.";
        }

        User user = resetToken.getUser();

        user.setPassword(passwordEncoder.encode(password));

        userRepository.save(user);

        return "Password changed successfully.";
    }
}