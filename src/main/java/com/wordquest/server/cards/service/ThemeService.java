package com.wordquest.server.cards.service;

import com.wordquest.server.cards.dto.ThemeDTO;
import com.wordquest.server.cards.model.Theme;
import com.wordquest.server.cards.model.ThemeRepository;
import com.wordquest.server.security.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ThemeService {
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private Security security;

    public void save(Long userId, String title, String description) {
        validateUser(userId);
        themeRepository.save(
                Theme.builder().userId(userId).title(title).description(description).build()
        );
    }

    public Page<ThemeDTO> findAllBy(Long userId, Pageable pageable) {
        validateUser(userId);
        return themeRepository.findAllByUserId(userId, pageable)
                .map(e -> ThemeDTO.builder()
                        .id(e.getId())
                        .description(e.getDescription())
                        .title(e.getTitle())
                        .build());
    }

    private void validateUser(Long userId) {
        if (!security.existsUserBy(userId)) {
            throw new BadCredentialsException("Bad user");
        }
    }
}