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
//    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
//    @JoinTable(name = "\"T_USER_WORD\"", joinColumns = @JoinColumn(name = "word_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id"))
//    @ManyToMany(mappedBy = "id.wordId", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "word")
    private Set<UserWord> userWords;


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
