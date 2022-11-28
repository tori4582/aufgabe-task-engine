package com.aufgabe.engine.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "workspace")
public class Workspace {

    @Id
    private String id;
    private String name;
    private String color;
    private String description;
    private List<Project> projects;
    private List<User> members;

}
