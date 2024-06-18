package com.wordquest.server.vocabulary.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"T_USER_WORD\"")
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
public class UserWord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word word;

    private String status;
}
