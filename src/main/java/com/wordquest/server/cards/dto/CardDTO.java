package com.wordquest.server.cards.dto;

import com.wordquest.server.cards.model.Word;
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
    private Long version;

}