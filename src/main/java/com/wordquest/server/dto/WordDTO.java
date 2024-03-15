package com.wordquest.server.dto;

import com.wordquest.server.entity.Word;

import java.io.Serializable;

public class WordDTO implements Serializable {
    private Word word;
    private String status;

    public WordDTO(Word word, String status) {
        this.word = word;
        this.status = status;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}