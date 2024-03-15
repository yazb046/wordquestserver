package com.wordquest.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"T_USER_WORD\"")
public class UserWord {

    @EmbeddedId
    private UserWordPK id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("wordId")
    @JoinColumn(name = "word_id")
    Word word;

    private String status;

    public UserWord() {
    }

    public UserWord(Long userId, Long wordId) {
        id = new UserWordPK(userId, wordId);
    }

}
