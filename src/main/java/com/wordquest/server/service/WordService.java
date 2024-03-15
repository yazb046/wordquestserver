package com.wordquest.server.service;

import com.wordquest.server.dto.WordDTO;
import com.wordquest.server.entity.UserWord;
import com.wordquest.server.entity.Word;
import com.wordquest.server.repository.UserWordRepository;
import com.wordquest.server.repository.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public List<Word> getAll() {
        return wordRepository.findAll();
    }

    public Page<Word> getAllBy(int pageNo, int pageSize, String sortBy, String direction) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.DESC;
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sortDirection, sortBy);

        return wordRepository.findAll(pageable);
    }


    public Optional<Word> get(Long wordId) {
        return wordRepository.findById(wordId);
    }

    public Word save(Long userId, Long wordId) {
        userWordRepository.save(new UserWord(userId, wordId));
        return wordRepository.findById(wordId).get();
    }

    public List<Word> getAllBy(Long userId) {
        return wordRepository.findAllBy(userId);
    }

    public Page<Word> getAllBy(long userId, int pageNo, int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, getSort(direction), sortBy);
        return wordRepository.findAllBy(userId, pageable);
    }

    public Page<WordDTO> getAllBy(long userId, String filter, int pageNo,
                                  int pageSize, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, getSort(direction), sortBy);
        return wordRepository.findAllByStatus(userId, filter, pageable);
    }

    private Sort.Direction getSort(String direction) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(direction)) {
            sortDirection = Sort.Direction.DESC;
        }
        return sortDirection;
    }
}
