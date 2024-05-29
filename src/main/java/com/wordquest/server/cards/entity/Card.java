package com.wordquest.server.cards.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"T_CARD\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Card {
    static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @Column(name = "is_archived")
    private Boolean isArchived;
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "word_id")
    private Long wordId;
    private Long version;
}