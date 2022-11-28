package com.aufgabe.engine.controller;

import com.aufgabe.engine.models.Project;
import com.aufgabe.engine.models.request.GeneralProjectRequest;
import com.aufgabe.engine.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static com.aufgabe.engine.common.ExceptionLogger.getNotImplementedResponse;
import static com.aufgabe.engine.common.ExceptionLogger.logInvalidAction;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/create")
    public ResponseEntity<?> createProject(@RequestBody GeneralProjectRequest req) {
        try {
            return ResponseEntity.ok().body(projectService.createProject(req));
        } catch (Exception e) {
            logInvalidAction(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProjectByName(@PathVariable String id) {
        try {
            return ResponseEntity.ok(projectService.getProject(id));
        } catch (NoSuchElementException e) {
            logInvalidAction(e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateProject(@RequestBody GeneralProjectRequest req) {
        return getNotImplementedResponse();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteProject(@RequestBody GeneralProjectRequest req) {
        try {
            return ResponseEntity.ok(projectService.deleteProject(req.getProjectId()));
        } catch (NoSuchElementException e) {
            logInvalidAction(e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/members/assign")
    public ResponseEntity<?> assignMembersToProject(@RequestBody GeneralProjectRequest req) {
        return getNotImplementedResponse();
    }

    @PostMapping("/members/remove")
    public ResponseEntity<?> removeMembersFromProject(@RequestBody GeneralProjectRequest req) {
        return getNotImplementedResponse();
    }

}
