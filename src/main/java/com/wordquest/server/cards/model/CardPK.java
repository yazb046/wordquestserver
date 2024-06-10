package com.wordquest.server.cards.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CardPK implements Serializable {

    static final long serialVersionUID = 1123L;

    private Long id;
    private Integer version;
}
