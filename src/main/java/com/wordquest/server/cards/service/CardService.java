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
}