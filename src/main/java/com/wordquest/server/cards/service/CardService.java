package com.wordquest.server.cards.service;

import com.wordquest.server.cards.dto.CardDTO;
import com.wordquest.server.cards.model.*;
import com.wordquest.server.vocabulary.model.WordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final WordRepository wordRepository;
    private final ThemeRepository themeRepository;

    public CardService(CardRepository cardRepository,
                       WordRepository wordRepository, ThemeRepository themeRepository) {
        this.cardRepository = cardRepository;
        this.wordRepository = wordRepository;
        this.themeRepository = themeRepository;
    }


//    public void save(Long userId, Long wordId, CardDTO cardDTO) {
//
//        Optional<User> isUser = userRepository.findById(userId);
//        if (isUser.isEmpty()) {
//            throw new RuntimeException("No such user");
//        }
//
//        Optional<Card> isCard = cardRepository.findByIdAndUserId(cardDTO.getId(), isUser.get().getId());
//        if (isCard.isPresent()) {
//            Card card = isCard.get();
//            card.setTitle(cardDTO.getTitle());
//            card.setContent(cardDTO.getContent());
//            card.setIsArchived(cardDTO.getIsArchived());
//            card.setVersion(card.getVersion() + 1L);
//            cardRepository.save(card);
//        } else {
//            Card card = Card.builder()
////                    .user(isUser.get())
//                    .userId(userId)
//                    .wordId(wordId)
//                    .title(cardDTO.getTitle())
//                    .content(cardDTO.getContent())
//                    .isArchived(false)
//                    .version(1L)
//                    .build();
//            cardRepository.save(card);
//        }
//    }
//
//    public void save(CardDTO cardDTO) {
//
//        Optional<User> isUser = userRepository.findById(cardDTO.getUserId());
//        if (isUser.isEmpty()) {
//            throw new RuntimeException("No such user");
//        }
//
//        Optional<Card> isCard = cardRepository.findByIdAndUserId(cardDTO.getId(), isUser.get().getId());
//        if (!isCard.isPresent()) throw new RuntimeException("No such card");
//        Card card = isCard.get();
//        card.setTitle(cardDTO.getTitle());
//        card.setContent(cardDTO.getContent());
//        card.setIsArchived(cardDTO.getIsArchived());
//        card.setVersion(card.getVersion() + 1L);
//        cardRepository.save(card);
//    }
//
//    public Page<CardDTO> getCardsBy(Long userId, Pageable pageable) {
//        Optional<User> isUser = userRepository.findById(userId);
//        if (isUser.isEmpty()) {
//            throw new RuntimeException("No such user");
//        }
//        Page<Card> result = cardRepository.findAllByUserId(isUser.get().getId(), pageable);
//        Set<Long> wordIds = new HashSet(result.getContent().stream().map(card -> card.getWordId()).toList());
//        List<Word> words =  wordRepository.findAllWordsBy(wordIds);
//        List<CardDTO> temp = result.getContent().stream().map(card -> CardDTO.builder()
//                .id(card.getId())
//                .title(card.getTitle())
//                .content(card.getContent())
//                .word(words.stream().filter(e-> Objects.equals(e.getId(), card.getWordId())).findFirst().orElse(null))
//                .isArchived(card.getIsArchived())
//                .version(card.getVersion())
//                .userId(card.getUserId()).build()).toList();
//        return new PageImpl<>(temp, result.getPageable(), result.getTotalElements());
//    }

    public Page<CardDTO> getAllCardsBy(Long themeId, Pageable pageable) {
        if (themeId == 0) {
            throw new RuntimeException("Bad input");
        }
        return cardRepository.findAllLatestByThemeId(themeId, pageable)
                .map(e -> CardDTO.builder()
                        .id(e.getPkid().getId())
                        .title(e.getTitle())
                        .content(e.getContent())
                        .build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CardDTO saveCard(Long themeId, CardDTO cardDTO) {
        if (!themeRepository.existsById(themeId)) {
            throw new RuntimeException("Bad input");
        }
        Card temp;
        if (cardDTO.getId() == 0) {
            Long lastId = cardRepository.findLastId().orElse(0L);
            temp = Card.builder()
                    .pkid(new CardPK(lastId + 1L, 1))
                    .themeId(themeId)
                    .title(cardDTO.getTitle())
                    .content(cardDTO.getContent())
                    .build();
        } else {
            Card savedCard = cardRepository.findLatestById(cardDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Bad input"));
            temp = Card.builder()
                    .pkid(
                            new CardPK(
                                    savedCard.getPkid().getId(),
                                    savedCard.getPkid().getVersion() + 1))
                    .themeId(themeId)
                    .title(cardDTO.getTitle())
                    .content(cardDTO.getContent())
                    .build();
        }

        temp = cardRepository.save(temp);
        return CardDTO.builder()
                .id(temp.getPkid().getId())
                .content(temp.getContent())
                .title(temp.getTitle())
                .build();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveCardOrder(Long themeId, List<Long> ids) {
        if (themeRepository.existsById(themeId)) {
            ids = ids.stream()
                    .filter(id -> id != null)
                    .collect(Collectors.toList());
            if (!ids.isEmpty()) {
                Optional<List<Card>> cards = cardRepository.findAllByIdsWithLatestVersions(ids);
                if (cards.isPresent()) {
                    cardRepository.saveAll(assignOrder(cards.get(), ids));
                }
            }
        }
    }

    public Page<CardDTO> getAllCardsOrdered(Long themeId, Pageable pageable) {
        if (!themeRepository.existsById(themeId)) {
            throw new RuntimeException("Bad input");
        }
        Page<CardDTO> result = cardRepository.findCardsByThemeIdOrdered(themeId, pageable)
                .map(e -> CardDTO.builder()
                        .id(e.getPkid().getId())
                        .title(e.getTitle())
                        .content(e.getContent())
                        .build());

        return result;

    }

    public List<Card> assignOrder(List<Card> result, List<Long> sortedIds) {

        Map<Long, Integer> idPositionMap = sortedIds.stream()
                .collect(Collectors.toMap(id -> id, sortedIds::indexOf));

        return result.stream()
                .filter(a -> idPositionMap.containsKey(a.getPkid().getId()))
                .peek(e -> e.setIndex(idPositionMap.get(e.getPkid().getId())))
                .collect(Collectors.toList());

    }
//    public List<CardDTO> sortCardDTOPageById(Page<CardDTO> result, List<Long> sortedIds) {
//        List<CardDTO> cardDTOList = result.getContent();
//
//        Map<Long, Integer> idPositionMap = sortedIds.stream()
//                .collect(Collectors.toMap(id -> id, sortedIds::indexOf));
//
//        List<CardDTO> top = cardDTOList.stream()
//                .filter(a -> idPositionMap.containsKey(a.getId()))
//                .sorted(Comparator.comparingInt(a -> idPositionMap.get(a.getId())))
//                .collect(Collectors.toList());
//
//        List<CardDTO> bottom = cardDTOList.stream()
//                .filter(a -> !idPositionMap.containsKey(a.getId()))
//                .collect(Collectors.toList());
//
//        top.addAll(bottom);
//
//        // Step 4: Convert the sorted list back into a Page<CardDTO>
//        return new PageImpl<>(top, result.getPageable(), result.getTotalElements());
//    }
}