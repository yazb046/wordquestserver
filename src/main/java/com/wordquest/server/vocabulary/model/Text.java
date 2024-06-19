package com.wordquest.server.vocabulary.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "\"T_TEXT\"")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Data
public class Text implements Serializable {
    static final long serialVersionUID = 19898L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private Boolean checked;
    @Column(name = "LANG_LEVEL")
    private String langLevel;
    private String source;
    @Column(name = "LINK_TO_AUDIO")
    private String linkToAudio;
    @Column(name = "CONTEXT_TITLE")
    private String contextTitle;
    private Long version;
    private String contextWord;
}