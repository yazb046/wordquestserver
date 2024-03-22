package com.wordquest.server.service;

import com.wordquest.server.dto.WordDTO;
import com.wordquest.server.entity.UserWord;
import com.wordquest.server.entity.Word;
import com.wordquest.server.repository.UserWordRepository;
import com.wordquest.server.repository.WordRepository;
import com.wordquest.server.utils.Helper;
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


    public Page<Word> getAll(Pageable pageable) {
        return wordRepository.findAll(pageable);
    }


    public Page<WordDTO> getAllBy(long userId, String filter, int pageNo,
                                  int pageSize, String sortBy, String direction) {
        return wordRepository.findAllByStatus(userId, filter, Helper.buildPageable(pageNo,pageSize,direction,sortBy));
    }

}
