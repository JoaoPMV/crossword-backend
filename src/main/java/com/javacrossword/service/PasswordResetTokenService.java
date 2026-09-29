package com.javacrossword.service;

import com.javacrossword.model.PasswordResetToken;
import com.javacrossword.model.User;
import com.javacrossword.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetTokenService(
            PasswordResetTokenRepository tokenRepository
    ) {
        this.tokenRepository = tokenRepository;
    }

    public PasswordResetToken createToken(User user) {

        PasswordResetToken resetToken = new PasswordResetToken();

        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setExpiration(
                LocalDateTime.now().plusMinutes(15)
        );
        resetToken.setUser(user);

        return tokenRepository.save(resetToken);
    }

    public PasswordResetToken findByToken(String token) {
    return tokenRepository.findByToken(token).orElse(null);
}

}