package com.wordquest.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"T_USER_WORD\"")
@Embeddable
public class UserWord {

    @EmbeddedId
    private UserWordPK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("wordId")
    @JoinColumn(name = "word_id")
    private Word word;

    private String status;

    public UserWord() {
    }

    public UserWord(Long userId, Long wordId) {
        id = new UserWordPK(userId, wordId);
    }

    public UserWordPK getId() {
        return id;
    }

    public void setId(UserWordPK id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
