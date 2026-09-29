  package com.javacrossword.service;

  import com.javacrossword.model.Level;
  import com.javacrossword.repository.LevelRepository;
  import org.springframework.stereotype.Service;

  import java.util.List;

  @Service
  public class LevelService {

      private final LevelRepository levelRepository;

      public LevelService(LevelRepository levelRepository) {
          this.levelRepository = levelRepository;
      }

      public List<Level> getAllLevels() {
          return levelRepository.findAll();
      }

      public Level createLevel(Level level) {
          return levelRepository.save(level);
      }

      public Level getLevelById(Long id) {
          return levelRepository.findById(id).orElse(null);
      }
  }