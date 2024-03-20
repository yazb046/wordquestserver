package com.wordquest.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "\"T_WORD\"")
@SecondaryTable(name = "\"T_USER_WORD\"", pkJoinColumns = @PrimaryKeyJoinColumn(name = "word_id"))
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
    @OneToMany(mappedBy = "word", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserWord> userWords;

    public Word() {
    }

    public Word(Long id, String word) {
        this.id = id;
        this.word = word;
    }

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

    public Set<UserWord> getUserWords() {
        return userWords;
    }

    public void setUserWords(Set<UserWord> userWords) {
        this.userWords = userWords;
    }

}
