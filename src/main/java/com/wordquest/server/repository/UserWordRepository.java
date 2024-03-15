package com.wordquest.server.repository;

import com.wordquest.server.entity.UserWord;
import com.wordquest.server.entity.UserWordPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRepository extends JpaRepository<UserWord, UserWordPK> {
}
