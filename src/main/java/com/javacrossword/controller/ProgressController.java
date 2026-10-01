package com.javacrossword.controller;

import com.javacrossword.model.Progress;
import com.javacrossword.service.ProgressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
public class ProgressController {

    private final ProgressService progressService;

    public ProgressController(ProgressService progressService) {
        this.progressService = progressService;
    }

    @GetMapping("/{userId}/{levelId}")
    public ResponseEntity<Progress> getProgress(
            @PathVariable Long userId,
            @PathVariable Long levelId
    ) {
        return progressService
                .findByUserIdAndLevelId(userId, levelId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Progress saveProgress(@RequestBody Progress progress) {
        return progressService.save(progress);
    }
}