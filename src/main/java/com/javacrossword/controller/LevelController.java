package com.javacrossword.controller;

import com.javacrossword.model.Level;
import com.javacrossword.service.LevelService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/levels")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping
    public List<Level> getAllLevels() {
        return levelService.getAllLevels();
    }

    @PostMapping
    public Level createLevel(@RequestBody Level level) {
        return levelService.createLevel(level);
    }

    @GetMapping("/{id}")
public Level getLevelById(@PathVariable Long id) {
    return levelService.getLevelById(id);
}
}

