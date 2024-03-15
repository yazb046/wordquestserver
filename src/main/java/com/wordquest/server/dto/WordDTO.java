package com.wordquest.server.dto;

import com.wordquest.server.entity.Word;

import java.io.Serializable;

public class WordDTO extends Word implements Serializable {
    private String status;

    public WordDTO(Word aWord, String status) {
        super.setId(aWord.getId());
        super.setWord(aWord.getWord());
        this.status = status;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}