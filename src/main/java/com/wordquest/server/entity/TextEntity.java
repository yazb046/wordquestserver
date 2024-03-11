package com.wordquest.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"T_TEXT\"")
public class TextEntity {
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

}