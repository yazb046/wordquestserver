package com.wordquest.server.cards.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "\"T_TEXT\"")
public class Text implements Serializable {
    static final long serialVersionUID = 1L;
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
    public Text() {
    }

    public Text(String text, Long version) {
        this.text = text;
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLinkToAudio() {
        return linkToAudio;
    }

    public void setLinkToAudio(String linkToAudio) {
        this.linkToAudio = linkToAudio;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}