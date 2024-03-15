package com.wordquest.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserWordPK implements Serializable {
    @Column(name = "user_id")
    private long userId;
    @Column(name = "word_id")
    private long wordId;

    public UserWordPK(long userId, long wordId) {
        this.userId = userId;
        this.wordId = wordId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserWordPK that)) return false;
        return userId == that.userId && wordId == that.wordId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, wordId);
    }
}
