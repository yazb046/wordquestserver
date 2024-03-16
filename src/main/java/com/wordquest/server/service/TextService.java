package com.wordquest.server.service;

import com.wordquest.server.entity.Text;
import com.wordquest.server.entity.Word;
import com.wordquest.server.repository.TextRepository;
import com.wordquest.server.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextService {
    private final TextRepository textRepository;
    private final WordRepository wordRepository;

    public TextService(TextRepository textRepository, WordRepository wordRepository) {
        this.textRepository = textRepository;
        this.wordRepository = wordRepository;
    }

    public List<Text> getAllByActiveWordsAndByUserId(Long userId, Long wordId) {
        String wordName = wordRepository.findById(wordId).orElse(new Word(0L,"")).getWord();
        return textRepository.getAllByActiveWordsAndByUserId(userId, wordName);
    }
}