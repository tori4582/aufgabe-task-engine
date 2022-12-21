package com.aufgabe.engine.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralTagRequest {
    private String id;
    private String title;
    private String color;
    private String desc;
}
