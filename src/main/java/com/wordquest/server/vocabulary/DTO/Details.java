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
public class Details implements Serializable {
    static final long serialVersionUID = 2L;
    private Boolean checked;
    private String langLevel;
    private String status;
}
