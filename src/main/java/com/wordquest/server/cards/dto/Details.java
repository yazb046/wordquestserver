package com.wordquest.server.cards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Details implements Serializable {
    static final long serialVersionUID = 2L;
    private Boolean checked;
    private String level;
    private String status;
    private String type;
}
