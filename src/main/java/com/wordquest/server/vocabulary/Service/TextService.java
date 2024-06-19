package com.wordquest.server.vocabulary.Service;

import com.wordquest.server.cards.utils.Helper;
import com.wordquest.server.vocabulary.DTO.Iterable;
import com.wordquest.server.vocabulary.model.TextRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TextService {
    private final TextRepository textRepository;

    public TextService(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    public Page<Iterable> getAllByActiveWordsAndByUserId(
            Long userId, String wordName, String filter, int pageNo,
            int pageSize, String sortBy, String direction
    ) {
        return textRepository.getAllByActiveWords(wordName,
                        Helper.buildPageable(pageNo, pageSize, direction, sortBy))
                .map(e -> Iterable.builder()
                        .id(e.getId())
                        .title(e.getText())
                        .build()
                );
    }
}