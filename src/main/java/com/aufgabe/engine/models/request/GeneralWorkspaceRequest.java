package com.aufgabe.engine.models.request;

import com.aufgabe.engine.models.Project;
import lombok.Data;

import java.util.List;

@Data
public class GeneralWorkspaceRequest {
    private String name;
    private String color;
    private String description;
}
