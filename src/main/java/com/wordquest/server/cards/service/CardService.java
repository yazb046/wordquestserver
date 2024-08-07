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
@Deprecated
//TODO replace with stepService
public class CardService {
    private final StepRepository stepRepository;
    private final WordRepository wordRepository;
    private final GoalsRepository goalsRepository;

    public CardService(StepRepository stepRepository,
                       WordRepository wordRepository, GoalsRepository goalsRepository) {
        this.stepRepository = stepRepository;
        this.wordRepository = wordRepository;
        this.goalsRepository = goalsRepository;
    }


    public Page<CardDTO> getAllCardsBy(Long themeId, Pageable pageable) {
        if (themeId == 0) {
            throw new RuntimeException("Bad input");
        }
        return stepRepository.findAllLatestByThemeId(themeId, pageable)
                .map(e -> CardDTO.builder()
                        .id(e.getPkid().getId())
                        .title(e.getTitle())
                        .content(e.getContent())
                        .build());
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public CardDTO saveCard(Long themeId, CardDTO cardDTO) {
        if (!goalsRepository.existsById(themeId)) {
            throw new RuntimeException("Bad input");
        }
        Step temp;
        if (cardDTO.getId() == 0) {
            Long lastId = stepRepository.findLastId().orElse(0L);
            temp = Step.builder()
                    .pkid(new StepPK(lastId + 1L, 1))
                    .goalId(themeId)
                    .title(cardDTO.getTitle())
                    .content(cardDTO.getContent())
                    .build();
        } else {
            Step savedStep = stepRepository.findLatestById(cardDTO.getId())
                    .orElseThrow(() -> new RuntimeException("Bad input"));
            temp = Step.builder()
                    .pkid(
                            new StepPK(
                                    savedStep.getPkid().getId(),
                                    savedStep.getPkid().getVersion() + 1))
                    .goalId(themeId)
                    .title(cardDTO.getTitle())
                    .content(cardDTO.getContent())
                    .build();
        }

        temp = stepRepository.save(temp);
        return CardDTO.builder()
                .id(temp.getPkid().getId())
                .content(temp.getContent())
                .title(temp.getTitle())
                .build();
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void saveCardOrder(Long themeId, List<Long> ids) {
        if (goalsRepository.existsById(themeId)) {
            ids = ids.stream()
                    .filter(id -> id != null)
                    .collect(Collectors.toList());
            if (!ids.isEmpty()) {
                Optional<List<Step>> cards = stepRepository.findAllByIdsWithLatestVersions(ids);
                if (cards.isPresent()) {
                    stepRepository.saveAll(assignOrder(cards.get(), ids));
                }
            }
        }
    }

    public Page<CardDTO> getAllCardsOrdered(Long themeId, Pageable pageable) {
        if (!goalsRepository.existsById(themeId)) {
            throw new RuntimeException("Bad input");
        }
        Page<CardDTO> result = stepRepository.findCardsByThemeIdOrdered(themeId, pageable)
                .map(e -> CardDTO.builder()
                        .id(e.getPkid().getId())
                        .title(e.getTitle())
                        .content(e.getContent())
                        .build());

        return result;

    }

    public List<Step> assignOrder(List<Step> result, List<Long> sortedIds) {

        Map<Long, Integer> idPositionMap = sortedIds.stream()
                .collect(Collectors.toMap(id -> id, sortedIds::indexOf));

        return result.stream()
                .filter(a -> idPositionMap.containsKey(a.getPkid().getId()))
                .peek(e -> e.setIndex(idPositionMap.get(e.getPkid().getId())))
                .collect(Collectors.toList());

    }
}