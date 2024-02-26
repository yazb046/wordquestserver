package com.wordquest.server.service;

import com.wordquest.server.entity.UserWordEntity;
import com.wordquest.server.entity.WordEntity;
import com.wordquest.server.repository.UserWordRepository;
import com.wordquest.server.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;

    public WordService(WordRepository wordRepository, UserWordRepository userWordRepository) {
        this.wordRepository = wordRepository;
        this.userWordRepository = userWordRepository;
    }

    public List<WordEntity> getAll() {
        return wordRepository.findAll();
    }

    public List<WordEntity> getAllByUserId(Long userId) {
        return wordRepository.findAllByUserId(userId);
    }

    public Optional<WordEntity> get(Long wordId) {
        return wordRepository.findById(wordId);
    }

    public WordEntity save(Long userId, Long wordId) {
        userWordRepository.save(new UserWordEntity(userId, wordId));
        return wordRepository.findById(wordId).get();
    }
}
