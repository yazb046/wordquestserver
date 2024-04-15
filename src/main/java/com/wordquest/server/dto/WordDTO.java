package com.wordquest.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WordDTO implements Serializable {
    static final long serialVersionUID = 1L;
    private Long id;
    private String word;
    private Boolean checked;
    private String langLevel;
    private String status;
}