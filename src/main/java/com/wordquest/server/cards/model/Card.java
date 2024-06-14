package com.wordquest.server.cards.model;

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
    @EmbeddedId
    private CardPK pkid;
    @Column(length = 240)
    private String title;
    @Column(length = 2400)
    private String content;
    @Column(name = "is_archived")
    private Boolean isArchived;
    private Long themeId;
}