package com.wordquest.server.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "\"T_USER\"")
@SecondaryTable(name = "\"T_USER_WORD\"", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String userName;

    @OneToMany(mappedBy = "user")
    private Set<UserWord> userWords;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<UserWord> getUserWords() {
        return userWords;
    }

    public void setUserWords(Set<UserWord> userWords) {
        this.userWords = userWords;
    }
}