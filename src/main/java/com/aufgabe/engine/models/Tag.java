package com.aufgabe.engine.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
public class Tag {

    @Id
    private String id;

    private String title;
    private String color;
    private String desc;
}
