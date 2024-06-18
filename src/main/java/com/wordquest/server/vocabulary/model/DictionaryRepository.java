package com.wordquest.server.vocabulary.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DictionaryRepository extends JpaRepository<Dictionary, Long> {
    Optional<Dictionary> findByDescription(String dictionaryName);
}
