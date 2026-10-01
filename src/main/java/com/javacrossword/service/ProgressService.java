package com.javacrossword.service;

import com.javacrossword.model.Progress;
import com.javacrossword.repository.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressService {

    private final ProgressRepository progressRepository;

    public ProgressService(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    public Optional<Progress> findByUserIdAndLevelId(Long userId, Long levelId) {
        return progressRepository.findByUserIdAndLevelId(userId, levelId);
    }

    public Progress save(Progress progress) {
        return progressRepository.save(progress);
    }
}