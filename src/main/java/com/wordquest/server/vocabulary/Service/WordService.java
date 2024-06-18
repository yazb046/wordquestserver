package com.wordquest.server.vocabulary.Service;

import com.wordquest.server.security.Security;
import com.wordquest.server.vocabulary.DTO.Details;
import com.wordquest.server.vocabulary.DTO.Iterable;
import com.wordquest.server.vocabulary.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordService {

    private final WordRepository wordRepository;
    private final UserWordRepository userWordRepository;

    private final DictionaryRepository dictionaryRepository;
    @Autowired
    private Security security;

    public WordService(WordRepository wordRepository,
                       UserWordRepository userWordRepository,
                       DictionaryRepository dictionaryRepository) {
        this.wordRepository = wordRepository;
        this.userWordRepository = userWordRepository;
        this.dictionaryRepository = dictionaryRepository;
    }


//    public Optional<Page<WordDTO>> getAllBy(Long userId, String status, String langLevel, Pageable pageable) {
//        boolean statusHasText = StringUtils.hasText(status);
//        boolean langLevelHasText = StringUtils.hasText(langLevel);
//
//        if (userId == null && !statusHasText && !langLevelHasText || "all".equalsIgnoreCase(langLevel)) {
//            return Optional.of(
//                    wordRepository.findAll(pageable)
//                            .map(e -> WordDTO.builder()
//                                    .id(e.getId())
//                                    .word(e.getWord())
//                                    .langLevel(e.getLangLevel())
//                                    .checked(e.getChecked())
//                                    .build())
//            );
//        }
//
////        if (userId == null && !statusIsEmpty && !langLevelHasText) {} //No sense to call status if userId is empty
//
//        if (userId == null && !statusHasText && langLevelHasText) {
//            return Optional.of(
//                    wordRepository.findAllByLangLevel(langLevel, pageable)
//                            .map(e -> WordDTO.builder()
//                                    .id(e.getId())
//                                    .word(e.getWord())
//                                    .langLevel(e.getLangLevel())
//                                    .checked(e.getChecked())
//                                    .build())
//            );
//        }
//
////        if (userId == null && !statusIsEmpty && langLevelHasText) {} //No sense to call status if userId is empty
//
//        if (userId != null && !statusHasText && !langLevelHasText) {
//            return Optional.of(
//                    wordRepository.findAllByUserId(userId, pageable)
//                            .map(e -> WordDTO.builder()
//                                    .id(e.getId())
//                                    .word(e.getWord())
//                                    .langLevel(e.getLangLevel())
//                                    .checked(e.getChecked())
//                                    .build())
//            );
//        }
//
//        if (userId != null && statusHasText && !langLevelHasText) {
//            return Optional.of(
//                    wordRepository.findAllByUserIdAndStatus(userId, status, pageable)
//            );
//        }
//
//        if (userId != null && !statusHasText && langLevelHasText) {
//            return Optional.of(
//                    wordRepository.findAllByUserIdAndLangLevel(userId, status, pageable)
//                            .map(e -> WordDTO.builder()
//                                    .id(e.getId())
//                                    .word(e.getWord())
//                                    .langLevel(e.getLangLevel())
//                                    .checked(e.getChecked())
//                                    .status(e.getStatus())
//                                    .build())
//            );
//        }
//
//        if (userId != null && statusHasText && langLevelHasText) {
//            return Optional.of(
//                    wordRepository.findAllByUserIAndLangLevelAndStatus(userId, langLevel, status, pageable)
//                            .map(e -> WordDTO.builder()
//                                    .id(e.getId())
//                                    .word(e.getWord())
//                                    .langLevel(e.getLangLevel())
//                                    .checked(e.getChecked())
//                                    .status(e.getStatus())
//                                    .build())
//            );
//        }
//
//        return Optional.empty();
//    }


    public Page<Iterable> getAllByDict(Long dictId, String langLevel, Pageable pageable) {
        Dictionary dict = dictionaryRepository.findById(dictId)
                .orElseThrow(() -> new RuntimeException("Bad input"));
        Set<String> langLevels = new HashSet<>();
        if ("all".equals(langLevel.toLowerCase())) {
            langLevels.add("A1");
            langLevels.add("A2");
        }
        return wordRepository.findAllByDictionaryAndLangLevelIn(dict, langLevels, pageable)
                .map(e -> Iterable.builder()
                        .id(e.getId())
                        .content(e.getWord())
                        .title(e.getWord())
                        .build());
    }

    public boolean saveWordsBy(Long userId, Set<Long> wordIds) {
        if (security.existsUserBy(userId)) {
            wordIds = wordIds.stream().filter(wordId -> wordId != null).collect(Collectors.toSet());
            List<Word> words = wordRepository.findAllWordsBy(wordIds);

            Set<UserWord> records =
                    wordIds.stream()
                            .map(wordId ->
                                    UserWord.builder()
                                            .userId(userId)
                                            .word(words.stream().filter(e -> e.getId() == wordId).findFirst().get())
                                            .build())
                            .collect(Collectors.toSet());
            if (!records.isEmpty()) {
                return !userWordRepository.saveAll(records).isEmpty();
            }
        }
        return false;
    }


    public List<Dictionary> getDictionaries() {
        return dictionaryRepository.findAll();
    }

    public Optional<Page<Iterable>> getAllUserWords(Long userId, Long dictId, Pageable pageable) {
        if (security.existsUserBy(userId)) {
            var dict = dictionaryRepository.findById(dictId).orElseThrow(() -> new RuntimeException("Bad input"));
            return Optional.of(
                    userWordRepository.findAllByUserIdAndWord_Dictionary(userId, dict, pageable)
                            .map(UserWord::getWord)
                            .map(word ->
                                    Iterable.builder()
                                            .id(word.getId())
                                            .title(word.getWord())
                                            .content(word.getWord())
                                            .details(Details.builder().langLevel(word.getLangLevel()).build())
                                            .build()
                            )
            );
        }
        return Optional.empty();
    }
}
