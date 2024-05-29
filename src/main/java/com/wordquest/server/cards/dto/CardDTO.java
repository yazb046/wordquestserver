package com.wordquest.server.cards.dto;

import com.wordquest.server.cards.entity.Word;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Setter
@Getter
public class CardDTO implements Serializable {

    private Long id;
    private String title;
    private String content;
    private Boolean isArchived;
    private Long userId;
    private Word word;
    private Long version;
}
