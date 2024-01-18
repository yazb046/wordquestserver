package com.wordquest.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserWordPK implements Serializable {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "word_id")
    private long wordId;

    public UserWordPK() {
    }

    public UserWordPK(long userId, long wordId) {
        this.userId = userId;
        this.wordId = wordId;
    }
}
