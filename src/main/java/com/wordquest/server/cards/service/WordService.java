package com.wordquest.server.cards.service;

import com.wordquest.server.cards.dto.WordDTO;
import com.wordquest.server.cards.model.User;
import com.wordquest.server.cards.model.UserWord;
import com.wordquest.server.cards.model.UserWordPK;
import com.wordquest.server.cards.model.Word;
import com.wordquest.server.cards.model.UserRepository;
import com.wordquest.server.cards.model.UserWordRepository;
import com.wordquest.server.cards.model.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;
    private final UserRepository userRepository;

    public WordService(WordRepository wordRepository, UserWordRepository userWordRepository, UserRepository userRepository) {
        this.wordRepository = wordRepository;
        this.userWordRepository = userWordRepository;
        this.userRepository = userRepository;
    }


    public Page<Word> getAll(Pageable pageable) {
        return wordRepository.findAll(pageable);
    }


    public Optional<Page<WordDTO>> getAllBy(Long userId, String status, String langLevel, Pageable pageable) {
        boolean statusHasText = StringUtils.hasText(status);
        boolean langLevelHasText = StringUtils.hasText(langLevel);

        if (userId == null && !statusHasText && !langLevelHasText || "all".equalsIgnoreCase(langLevel)) {
            return Optional.of(
                    wordRepository.findAll(pageable)
                            .map(e -> WordDTO.builder()
                                    .id(e.getId())
                                    .word(e.getWord())
                                    .langLevel(e.getLangLevel())
                                    .checked(e.getChecked())
                                    .build())
            );
        }

//        if (userId == null && !statusIsEmpty && !langLevelHasText) {} //No sense to call status if userId is empty

        if (userId == null && !statusHasText && langLevelHasText) {
            return Optional.of(
                    wordRepository.findAllByLangLevel(langLevel, pageable)
                            .map(e -> WordDTO.builder()
                                    .id(e.getId())
                                    .word(e.getWord())
                                    .langLevel(e.getLangLevel())
                                    .checked(e.getChecked())
                                    .build())
            );
        }

//        if (userId == null && !statusIsEmpty && langLevelHasText) {} //No sense to call status if userId is empty

        if (userId != null && !statusHasText && !langLevelHasText) {
            return Optional.of(
                    wordRepository.findAllByUserId(userId, pageable)
                            .map(e -> WordDTO.builder()
                                    .id(e.getId())
                                    .word(e.getWord())
                                    .langLevel(e.getLangLevel())
                                    .checked(e.getChecked())
                                    .build())
            );
        }

        if (userId != null && statusHasText && !langLevelHasText) {
            return Optional.of(
                    wordRepository.findAllByUserIdAndStatus(userId, status, pageable)
            );
        }

        if (userId != null && !statusHasText && langLevelHasText) {
            return Optional.of(
                    wordRepository.findAllByUserIdAndLangLevel(userId, status, pageable)
                            .map(e -> WordDTO.builder()
                                    .id(e.getId())
                                    .word(e.getWord())
                                    .langLevel(e.getLangLevel())
                                    .checked(e.getChecked())
                                    .status(e.getStatus())
                                    .build())
            );
        }

        if (userId != null && statusHasText && langLevelHasText) {
            return Optional.of(
                    wordRepository.findAllByUserIAndLangLevelAndStatus(userId, langLevel, status, pageable)
                            .map(e -> WordDTO.builder()
                                    .id(e.getId())
                                    .word(e.getWord())
                                    .langLevel(e.getLangLevel())
                                    .checked(e.getChecked())
                                    .status(e.getStatus())
                                    .build())
            );
        }

        return Optional.empty();
    }

    public Optional<List<Word>> getAllBy(String wordLetters) {
        return Optional.of(wordRepository.findAllByWordContaining(wordLetters));
    }

    public void saveWordsBy(Long userId, List<WordDTO> wordsDTO) {
        Optional<User> isUser = userRepository.findById(userId);
        if (!isUser.isPresent()) throw new RuntimeException("No such user");
        User user = isUser.get();
        Set<Long> wordIds = wordsDTO.stream().map(word -> word.getId()).collect(Collectors.toSet());
        List<Word> words = wordRepository.findAllWordsBy(wordIds);
        if (wordsDTO.size() != words.size()) throw new RuntimeException("No such words");;
        for (Word word : words) {
            UserWord userWord = new UserWord();
            UserWordPK userWordPK = new UserWordPK();
            userWordPK.setUserId(user.getId());
            userWordPK.setWordId(word.getId());
            userWord.setId(userWordPK);
            userWord.setUser(user);
            userWord.setWord(word);
            userWord.setStatus("new");
            user.getUserWords().add(userWord);
        }
        userRepository.save(user);
    }
}
