package com.javacrossword.repository;

import com.javacrossword.model.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevokedTokenRepository
        extends JpaRepository<RevokedToken, Long> {

    boolean existsByTokenHash(String tokenHash);
}