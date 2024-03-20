package com.wordquest.server.repository;

import com.wordquest.server.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CardRepository extends JpaRepository<Card, Long> {

}