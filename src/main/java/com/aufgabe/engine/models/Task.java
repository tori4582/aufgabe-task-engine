package com.aufgabe.engine.models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Task {

    private String id;
    private String title;
    private String description;
    private Document document;
    private String imageUrl;
    private List<Tag> attachedTags;
    private List<String> associatedTasks;
    private List<User> assignedUsers;
    private User createdUser;
    private LocalDateTime createdDateTime;
    private LocalDateTime lastModifiedDateTime;
    private LocalDateTime dateTime;

    public static class Document {

    }
}
