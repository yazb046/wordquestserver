package com.wordquest.server.cards.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardOrderRepository extends JpaRepository<CardOrder, Long> {
}