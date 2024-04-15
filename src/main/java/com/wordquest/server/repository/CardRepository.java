package com.wordquest.server.repository;

import com.wordquest.server.dto.CardDTO;
import com.wordquest.server.entity.Card;
import com.wordquest.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;



public interface CardRepository extends JpaRepository<Card, Long> {

    Page<Card> findAllByUserId(Long userId, Pageable pageable);

    Page<Card> findAllByUserIdAndTitleContaining(Long userId, String title,Pageable pageable);
    Optional<Card> findByIdAndUserId(Long cardId, Long userId);
}