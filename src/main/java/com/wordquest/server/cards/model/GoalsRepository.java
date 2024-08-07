package com.wordquest.server.cards.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalsRepository extends JpaRepository<Goal, Long> {

    Page<Goal> findAllByUserId(Long userId, Pageable pageable);
}