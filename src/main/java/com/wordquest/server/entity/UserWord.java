package com.wordquest.server.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"T_USER_WORD\"")
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
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
}
