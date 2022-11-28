package com.aufgabe.engine.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@Builder
public class Status {

    @Id
    private String id;
    private String name;
    private String description;
    private String color;
    private List<String> tasks;
}
