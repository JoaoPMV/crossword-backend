package com.javacrossword.repository;

import com.javacrossword.model.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {

 boolean existsByName(String name);
}