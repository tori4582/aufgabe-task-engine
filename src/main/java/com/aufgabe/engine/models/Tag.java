package com.aufgabe.engine.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
public class Tag {
    private String id;
    private String title;
    private String color;
}
