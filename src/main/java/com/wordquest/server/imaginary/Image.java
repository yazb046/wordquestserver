package com.wordquest.server.imaginary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "images")
@Builder
@Getter
@Setter
public class Image {

    @Id
    private String id;
    private Binary image;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Long themeId;

}