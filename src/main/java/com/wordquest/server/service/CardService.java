package com.wordquest.server.service;

import com.wordquest.server.dto.CardDTO;
import com.wordquest.server.entity.Card;
import com.wordquest.server.entity.User;
import com.wordquest.server.entity.Word;
import com.wordquest.server.repository.CardRepository;
import com.wordquest.server.repository.UserRepository;
import com.wordquest.server.repository.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository, WordRepository wordRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
    }


    public void save(Long userId, Long wordId, CardDTO cardDTO) {

        Optional<User> isUser = userRepository.findById(userId);
        if (isUser.isEmpty()) {
            throw new RuntimeException("No such user");
        }

        Optional<Card> isCard = cardRepository.findByIdAndUserId(cardDTO.getId(), isUser.get().getId());
        if (isCard.isPresent()) {
            Card card = isCard.get();
            card.setTitle(cardDTO.getTitle());
            card.setContent(cardDTO.getContent());
            card.setIsArchived(cardDTO.getIsArchived());
            card.setVersion(card.getVersion() + 1L);
            cardRepository.save(card);
        } else {
            Card card = Card.builder()
//                    .user(isUser.get())
                    .userId(userId)
                    .wordId(wordId)
                    .title(cardDTO.getTitle())
                    .content(cardDTO.getContent())
                    .isArchived(false)
                    .version(1L)
                    .build();
            cardRepository.save(card);
        }
    }

    public void save(CardDTO cardDTO) {

        Optional<User> isUser = userRepository.findById(cardDTO.getUserId());
        if (isUser.isEmpty()) {
            throw new RuntimeException("No such user");
        }

        Optional<Card> isCard = cardRepository.findByIdAndUserId(cardDTO.getId(), isUser.get().getId());
        if (!isCard.isPresent()) throw new RuntimeException("No such card");
        Card card = isCard.get();
        card.setTitle(cardDTO.getTitle());
        card.setContent(cardDTO.getContent());
        card.setIsArchived(cardDTO.getIsArchived());
        card.setVersion(card.getVersion() + 1L);
        cardRepository.save(card);
    }

    public Page<CardDTO> getCardsBy(Long userId, Pageable pageable) {
        Optional<User> isUser = userRepository.findById(userId);
        if (isUser.isEmpty()) {
            throw new RuntimeException("No such user");
        }
        Page<Card> result = cardRepository.findAllByUserId(isUser.get().getId(), pageable);
        Set<Long> wordIds = new HashSet(result.getContent().stream().map(card -> card.getWordId()).toList());
        List<Word> words =  wordRepository.findAllWordsBy(wordIds);
        List<CardDTO> temp = result.getContent().stream().map(card -> CardDTO.builder()
                .id(card.getId())
                .title(card.getTitle())
                .content(card.getContent())
                .word(words.stream().filter(e-> Objects.equals(e.getId(), card.getWordId())).findFirst().orElse(null))
                .isArchived(card.getIsArchived())
                .version(card.getVersion())
                .userId(card.getUserId()).build()).toList();
        return new PageImpl<>(temp, result.getPageable(), result.getTotalElements());
    }
}