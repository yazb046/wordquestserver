package com.wordquest.server.cards.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"T_GOAL_TYPE\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GoalType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
}