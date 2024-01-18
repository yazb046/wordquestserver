package com.wordquest.server.repository;

import com.wordquest.server.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
}
