package com.wordquest.server.vocabulary.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Iterable implements Serializable {
    static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    private String content;
    private Details details;
}