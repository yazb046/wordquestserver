package com.wordquest.server.cards.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "\"T_CARD_ORDER\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CardOrder {
    static final long serialVersionUID = 2L;

    @Id
    private Long themeId;

    @ElementCollection
    @CollectionTable(name = "T_CARD_ORDER_TOP_IDS", joinColumns = @JoinColumn(name = "item_id"))
    @Column(name = "item")
    private List<Long> topCardIds;
}