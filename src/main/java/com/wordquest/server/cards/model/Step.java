package com.wordquest.server.cards.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "\"T_STEPS\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Step implements Serializable {
    static final long serialVersionUID = 2L;

    @EmbeddedId
    private StepPK pkid;
    @Column(length = 240)
    private String title;
    @Column(length = 2400)
    private String content;
    @Column(name = "is_archived")
    private Boolean isArchived;
    private Long goalId;
    private Integer index;
}