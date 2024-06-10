package com.wordquest.server.cards.service;

import com.wordquest.server.cards.model.Text;
import com.wordquest.server.cards.model.TextRepository;
import com.wordquest.server.cards.model.UserRepository;
import com.wordquest.server.cards.model.WordRepository;
import com.wordquest.server.cards.utils.Helper;
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