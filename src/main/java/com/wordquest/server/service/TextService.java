package com.wordquest.server.service;

import com.wordquest.server.entity.TextEntity;
import com.wordquest.server.repository.TextRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextService {
    private final TextRepository textRepository;

    public TextService(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    public List<TextEntity> getAllByActiveWordsAndByUserId(Long userId) {
        return textRepository.getAllByActiveWordsAndByUserId(userId);
    }
}