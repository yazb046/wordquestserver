package com.wordquest.server.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "\"T_USER_WORD\"")
public class UserWordEntity {

    @EmbeddedId
    private UserWordPK id;

    public UserWordEntity() {
    }

    public UserWordEntity(Long userId, Long wordId) {
        id = new UserWordPK(userId, wordId);
    }
}
