package com.wordquest.server.vocabulary.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRepository extends JpaRepository<UserWord, Long> {
    Page<UserWord> findAllByUserIdAndWord_Dictionary(Long userId, Dictionary dictionary, Pageable pageable);
}
