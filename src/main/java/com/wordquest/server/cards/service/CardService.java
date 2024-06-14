package com.wordquest.server.cards.service;

import com.wordquest.server.cards.dto.CardDTO;
import com.wordquest.server.cards.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final WordRepository wordRepository;
    private final ThemeRepository themeRepository;

    private final CardOrderRepository cardOrderRepository;

    public CardService(CardRepository cardRepository, UserRepository userRepository, WordRepository wordRepository, ThemeRepository themeRepository, CardOrderRepository cardOrderRepository) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.wordRepository = wordRepository;
        this.themeRepository = themeRepository;
        this.cardOrderRepository = cardOrderRepository;
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

    public void saveCardOrder(Long themeId, List<Long> ids) {
        if (themeRepository.existsById(themeId)) {
            ids = ids.stream()
                    .filter(id -> id != null)
                    .collect(Collectors.toList());
            if (!ids.isEmpty()) {
                cardOrderRepository.save(
                        CardOrder.builder()
                                .themeId(themeId)
                                .topCardIds(ids)
                                .build()
                );
            }
        }
    }

    public Page<CardDTO> getAllCardsOrderedBy(Long themeId, Pageable pageable) {
        if (!themeRepository.existsById(themeId)) {
            throw new RuntimeException("Bad input");
        }
        Page<CardDTO> result = cardRepository
                .findAllLatestByThemeId(themeId, pageable)
                .map(e -> CardDTO.builder()
                        .id(e.getPkid().getId())
                        .title(e.getTitle())
                        .content(e.getContent())
                        .build());

        Optional<CardOrder> order = cardOrderRepository.findById(themeId);
        if (order.isEmpty()) {
            return result;
        } else {
            return sortCardDTOPageById(result, order.get().getTopCardIds());
        }
    }

    public Page<CardDTO> sortCardDTOPageById(Page<CardDTO> result, List<Long> sortedIds) {
        // Step 1: Extract the list of CardDTO from the Page
        List<CardDTO> cardDTOList = result.getContent();

        // Step 2: Create a map from the provided list of IDs to their positions
        Map<Long, Integer> idPositionMap = sortedIds.stream()
                .collect(Collectors.toMap(id -> id, sortedIds::indexOf));

        // Step 3: Sort the list of CardDTO based on the order of the IDs from the provided list
        List<CardDTO> sortedCardDTOList = cardDTOList.stream()
                .sorted(Comparator.comparingInt(a -> idPositionMap.get(a.getId())))
                .collect(Collectors.toList());

        // Step 4: Convert the sorted list back into a Page<CardDTO>
        return new PageImpl<>(sortedCardDTOList, result.getPageable(), result.getTotalElements());
    }
}