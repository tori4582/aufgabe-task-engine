package com.aufgabe.engine.models.request;

import com.aufgabe.engine.models.Status;
import com.aufgabe.engine.models.Task;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
@Builder
public class GeneralProjectRequest {

    private String projectId;
    private String name;
    private String color;
    private String description;
    private List<Status> statuses;
    private List<Task> tasks;
    private List<String> tagIds;
    private List<String> memberUsernames;

}
