package com.wordquest.server.cards.service;

import com.wordquest.server.cards.dto.Details;
import com.wordquest.server.cards.dto.Iterable;
import com.wordquest.server.cards.dto.ThemeDTO;
import com.wordquest.server.cards.model.GoalType;
import com.wordquest.server.cards.model.GoalTypeRepository;
import com.wordquest.server.cards.model.Goal;
import com.wordquest.server.cards.model.GoalsRepository;
import com.wordquest.server.security.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Deprecated
//TODO replace with goalService
public class ThemeService {
    @Autowired
    private GoalsRepository goalsRepository;
    @Autowired
    private GoalTypeRepository goalTypeRepository;
    @Autowired
    private Security security;

    public void save(Long userId, ThemeDTO theme) {
        validateUser(userId);
        Optional<GoalType> addOn = goalTypeRepository.findById(theme.getThemeTypeId());
        if (addOn.isPresent()) {
            goalsRepository.save(
                    Goal.builder().userId(userId)
                            .title(theme.getTitle())
                            .description(theme.getDescription())
                            .goalType(addOn.get())
                            .build()
            );
        }

    }

    public Page<Iterable> findAllBy(Long userId, Pageable pageable) {
        validateUser(userId);
        return goalsRepository.findAllByUserId(userId, pageable)
                .map(e -> Iterable.builder()
                        .id(e.getId())
                        .content(e.getDescription())
                        .title(e.getTitle())
                        .details(Details.builder()
                                .type(e.getGoalType() == null ? "" : e.getGoalType().getDescription()
                                ).build())
                        .build());
    }

    private void validateUser(Long userId) {
        if (!security.existsUserBy(userId)) {
            throw new BadCredentialsException("Bad user");
        }
    }

    public List<GoalType> getAllThemeTypes() {
        return goalTypeRepository.findAll();
    }
}