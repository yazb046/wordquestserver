package com.wordquest.server.cards.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"T_THEME\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(length = 500)
    private String description;
    private Long userId;
}