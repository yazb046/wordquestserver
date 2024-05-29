package com.wordquest.server.cards.repository;

import com.wordquest.server.cards.entity.UserWord;
import com.wordquest.server.cards.entity.UserWordPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRepository extends JpaRepository<UserWord, UserWordPK> {
}
