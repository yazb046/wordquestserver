package com.wordquest.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "\"T_CARD\"")
public class Card {
    static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "CONTEXT_TITLE")
    private String contextTitle;
    private String text;
    @Column(name = "HELP_TEXT")
    private String helpText;
    @Column(name = "is_archived")
    private Boolean isArchived;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Column(name = "text_id")
    private Long textId;
    private Long version;


    public Card(String contextTitle, String text, Long textId, Long version) {
        this.contextTitle = contextTitle;
        this.text = text;
        this.textId = textId;
        this.version = version;
    }

    public Card() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContextTitle() {
        return contextTitle;
    }

    public void setContextTitle(String contextTitle) {
        this.contextTitle = contextTitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHelpText() {
        return helpText;
    }

    public void setHelpText(String helpText) {
        this.helpText = helpText;
    }

    public Boolean getArchived() {
        return isArchived;
    }

    public void setArchived(Boolean archived) {
        isArchived = archived;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getTextId() {
        return textId;
    }

    public void setTextId(Long textId) {
        this.textId = textId;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}