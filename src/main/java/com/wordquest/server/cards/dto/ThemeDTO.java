package com.wordquest.server.cards.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ThemeDTO {
    private Long id;
    private String title;
    private String description;
    private Integer addOn;
}