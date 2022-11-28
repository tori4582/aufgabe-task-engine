package com.aufgabe.engine.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document
public class Project {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String color;
    private String description;
    private List<String> statusIds;
    private List<Task> tasks;
    private PermissionSettings permissions;
    private List<Tag> tags;
    private List<User> members;
}
