package com.wordquest.server.cards.service;

import com.wordquest.server.cards.dto.Details;
import com.wordquest.server.cards.dto.Iterable;
import com.wordquest.server.cards.dto.ThemeDTO;
import com.wordquest.server.cards.model.ThemeType;
import com.wordquest.server.cards.model.ThemeTypeRepository;
import com.wordquest.server.cards.model.Theme;
import com.wordquest.server.cards.model.ThemeRepository;
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
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private ThemeTypeRepository themeTypeRepository;
    @Autowired
    private Security security;

    public void save(Long userId, ThemeDTO theme) {
        validateUser(userId);
        Optional<ThemeType> addOn = themeTypeRepository.findById(theme.getThemeTypeId());
        if (addOn.isPresent()) {
            themeRepository.save(
                    Theme.builder().userId(userId)
                            .title(theme.getTitle())
                            .description(theme.getDescription())
                            .themeType(addOn.get())
                            .build()
            );
        }

    }

    public Page<Iterable> findAllBy(Long userId, Pageable pageable) {
        validateUser(userId);
        return themeRepository.findAllByUserId(userId, pageable)
                .map(e -> Iterable.builder()
                        .id(e.getId())
                        .content(e.getDescription())
                        .title(e.getTitle())
                        .details(Details.builder()
                                .type(e.getThemeType() == null ? "" : e.getThemeType().getDescription()
                                ).build())
                        .build());
    }

    private void validateUser(Long userId) {
        if (!security.existsUserBy(userId)) {
            throw new BadCredentialsException("Bad user");
        }
    }

    public List<ThemeType> getAllThemeTypes() {
        return themeTypeRepository.findAll();
    }
}