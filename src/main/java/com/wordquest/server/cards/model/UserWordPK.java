package com.wordquest.server.cards.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserWordPK implements Serializable {
    static final long serialVersionUID = 1L;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "word_id")
    private long wordId;

    public UserWordPK(long userId, long wordId) {
        this.userId = userId;
        this.wordId = wordId;
    }

    public UserWordPK(){}

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }
}
