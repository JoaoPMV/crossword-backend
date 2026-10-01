package com.javacrossword.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key = Keys.hmacShaKeyFor(
        "minha-chave-secreta-com-pelo-menos-32-caracteres"
            .getBytes()
    );

    public String generateToken(String email, Long userId) {

        return Jwts.builder()
            .subject(email)
            .claim("userId", userId)
            .issuedAt(new Date())
            .expiration(
                new Date(System.currentTimeMillis() + 1000 * 60 * 60)
            )
            .signWith(key)
            .compact();
    }

    public String extractEmail(String token) {

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
    }

    public Long extractUserId(String token) {

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("userId", Long.class);
    }

    public Date extractExpiration(String token) {

        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getExpiration();
    }
}