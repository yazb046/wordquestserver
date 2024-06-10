package com.wordquest.server.cards.model;

import com.wordquest.server.cards.model.UserWord;
import com.wordquest.server.cards.model.UserWordPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRepository extends JpaRepository<UserWord, UserWordPK> {
}
