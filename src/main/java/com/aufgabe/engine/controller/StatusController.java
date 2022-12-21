package com.aufgabe.engine.controller;

import com.aufgabe.engine.models.Project;
import com.aufgabe.engine.models.Status;
import com.aufgabe.engine.models.request.GeneralStatusRequest;
import com.aufgabe.engine.service.ProjectService;
import com.aufgabe.engine.service.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.NoSuchElementException;

import static com.aufgabe.engine.common.ApplicationUtils.controllerWrapper;
import static com.aufgabe.engine.common.ExceptionLogger.logInvalidAction;
import static java.util.Optional.ofNullable;

@Controller
@RequiredArgsConstructor
public class StatusController {

    private final StatusService statusService;
    private final ProjectService projectService;

    @PostMapping("/projects/{projectId}/status/create")
    public ResponseEntity<?> createStatus(@PathVariable String projectId,
                                          @RequestBody GeneralStatusRequest req) {
        try {
            Project project = projectService.getProject(projectId);
            Status preparedStatus = Status.builder()
                    .name(req.getStatusName())
                    .description(req.getDescription())
                    .color(req.getColor())
                    .build();
            return ResponseEntity.ok(statusService.createStatus(
                    project,
                    preparedStatus,
                    req.getPlacementOrder()
            ));
        } catch (NoSuchElementException e) {
            logInvalidAction(e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/projects/{projectId}/status")
    public ResponseEntity<?> getStatusesOfProject(@PathVariable String projectId) {

        return controllerWrapper(() -> {
            Project project = projectService.getProject(projectId);
            return ofNullable(project.getStatusIds()).orElse(Collections.emptyList());
        });
    }

    @DeleteMapping("/projects/{projectId}/status/{statusId}")
    public ResponseEntity<?> deleteStatus(@PathVariable String projectId,
                                          @PathVariable String statusId) {
        try {
            Project project = projectService.getProject(projectId);
            return ResponseEntity.ok(statusService.removeStatus(project, statusId));
        } catch (NoSuchElementException e) {
            logInvalidAction(e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/projects/{projectId}/status/{statusId}")
    public ResponseEntity<?> getStatusDetails(@PathVariable String projectId,
                                       @PathVariable String statusId) {
        return controllerWrapper(() -> {
            Project project = projectService.getProject(projectId);
            return statusService.getStatusDetails(project, statusId);
        });
    }

}
