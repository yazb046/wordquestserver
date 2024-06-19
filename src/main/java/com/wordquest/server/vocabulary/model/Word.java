package com.wordquest.server.vocabulary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "\"T_WORD\"")
//@SecondaryTable(name = "\"T_USER_WORD\"", pkJoinColumns = @PrimaryKeyJoinColumn(name = "word_id"))
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Getter
@Setter
public class Word implements Serializable {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private Boolean checked;
    @Column(name = "LANG_LEVEL")
    private String langLevel;
    @JsonIgnore
    @ManyToOne
    private Dictionary dictionary;

}
