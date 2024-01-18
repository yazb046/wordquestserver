package com.wordquest.server.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "\"T_WORD\"")
public class WordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private Boolean checked;
    @Column(name = "LANG_LEVEL")
    private String langLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getLangLevel() {
        return langLevel;
    }

    public void setLangLevel(String langLevel) {
        this.langLevel = langLevel;
    }
}
