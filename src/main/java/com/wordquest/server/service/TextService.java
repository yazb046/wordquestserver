package com.wordquest.server.service;

import com.wordquest.server.entity.Text;
import com.wordquest.server.repository.TextRepository;
import com.wordquest.server.repository.UserRepository;
import com.wordquest.server.repository.WordRepository;
import com.wordquest.server.utils.Helper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextService {
    private final TextRepository textRepository;
    private final WordRepository wordRepository;
    private final UserRepository userRepository;

    public TextService(TextRepository textRepository, WordRepository wordRepository, UserRepository userRepository) {
        this.textRepository = textRepository;
        this.wordRepository = wordRepository;
        this.userRepository = userRepository;
    }

    public Page<List<Text>> getAllByActiveWordsAndByUserId(Long userId, String wordName, String filter, int pageNo,
                                                           int pageSize, String sortBy, String direction) {
        return textRepository.getAllByActiveWordsAndByUserId(userId, wordName,
                Helper.buildPageable(pageNo, pageSize, direction, sortBy));
    }

}