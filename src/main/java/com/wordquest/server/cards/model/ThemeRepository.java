package com.wordquest.server.cards.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    Page<Theme> findAllByUserId(Long userId, Pageable pageable);
}