package com.wordquest.server.repository;

import com.wordquest.server.entity.UserWordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWordRepository extends JpaRepository<UserWordEntity, Long> {
}
