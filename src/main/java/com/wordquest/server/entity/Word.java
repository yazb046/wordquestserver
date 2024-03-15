package com.wordquest.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "\"T_WORD\"")
@SecondaryTable(name = "\"T_USER_WORD\"", pkJoinColumns = @PrimaryKeyJoinColumn(name = "word_id"))
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    private Boolean checked;
    @Column(name = "LANG_LEVEL")
    private String langLevel;

    @JsonIgnore
    @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
    private Set<UserWord> userWords;

//    @OneToOne(mappedBy = "word", cascade = CascadeType.ALL)
//    private String status;

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
