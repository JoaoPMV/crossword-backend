package com.javacrossword.service;

import com.javacrossword.model.RevokedToken;
import com.javacrossword.repository.RevokedTokenRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class TokenRevocationService {

    private final RevokedTokenRepository revokedTokenRepository;

    public TokenRevocationService(
            RevokedTokenRepository revokedTokenRepository
    ) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    public void revokeToken(
            String token,
            LocalDateTime expiresAt
    ) {
        String tokenHash = hashToken(token);

        if (!revokedTokenRepository.existsByTokenHash(tokenHash)) {
            revokedTokenRepository.save(
                    new RevokedToken(tokenHash, expiresAt)
            );
        }
    }

    public boolean isRevoked(String token) {
        String tokenHash = hashToken(token);

        return revokedTokenRepository
                .existsByTokenHash(tokenHash);
    }

    private String hashToken(String token) {

        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(
                            token.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hexString =
                    new StringBuilder();

            for (byte b : hash) {
                hexString.append(
                        String.format("%02x", b)
                );
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(
                    "Erro ao gerar hash do token",
                    e
            );
        }
    }
}