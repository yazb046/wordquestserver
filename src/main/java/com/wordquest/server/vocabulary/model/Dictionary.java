package com.wordquest.server.vocabulary.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "\"T_DICT\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
public class Dictionary implements Serializable {
    static final long serialVersionUID = 15345345L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @OneToMany
    private List<Word> words;
}
