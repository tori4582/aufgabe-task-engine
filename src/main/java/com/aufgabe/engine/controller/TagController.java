package com.aufgabe.engine.controller;

import com.aufgabe.engine.models.Project;
import com.aufgabe.engine.models.Tag;
import com.aufgabe.engine.models.request.GeneralTagRequest;
import com.aufgabe.engine.service.ProjectService;
import com.aufgabe.engine.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

import static com.aufgabe.engine.common.ApplicationUtils.controllerWrapper;
import static com.aufgabe.engine.common.ExceptionLogger.getNotImplementedResponse;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class TagController {

    private final ProjectService projectService;
    private final TagService tagService;

    @GetMapping("/{projectId}/tags")
    public ResponseEntity<?> getProjectTags(@PathVariable String projectId) {
        return controllerWrapper(() -> {
            Project project = projectService.getProject(projectId);
            return tagService.getTags(project);
        });
    }


    @PostMapping("/{projectId}/tags/create")
    public ResponseEntity<?> createTag(@PathVariable String projectId,
                                    @RequestBody GeneralTagRequest generalTagRequest) {
        return controllerWrapper(() -> {
            Project project = projectService.getProject(projectId);
            return tagService.createTag(project, generalTagRequest);
        });
    }

    @DeleteMapping("/{projectId}/tags/{tagId}")
    public ResponseEntity<?> deleteTag(@PathVariable String projectId,
                                    @PathVariable String tagId) {
        return controllerWrapper(() -> {
            Project project = projectService.getProject(projectId);
            return tagService.removeTag(project, tagId);
        });
    }

    @PostMapping("/{projectId}/tags/{tagId}")
    public ResponseEntity<?> updateTag(@PathVariable String projectId,
                                       @PathVariable String tagId,
                                       @RequestBody GeneralTagRequest req) {
        return getNotImplementedResponse();
    }

    @DeleteMapping("/{projectId}/tags")
    public ResponseEntity<?> deleteAllTags(@PathVariable String projectId) {
        return controllerWrapper(() -> {
           Project project = projectService.getProject(projectId);
           return tagService.removeAllTags(project);
        });
    }
}
