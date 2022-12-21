package com.aufgabe.engine.controller;

import com.aufgabe.engine.models.request.GeneralWorkspaceRequest;
import com.aufgabe.engine.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aufgabe.engine.common.ApplicationUtils.controllerWrapper;
import static com.aufgabe.engine.common.ExceptionLogger.getNotImplementedResponse;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkspace(@PathVariable String id) {
        return controllerWrapper(() -> workspaceService.getWorkspaceWithId(id));
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllWorkspaces() {
        return controllerWrapper(workspaceService::getAllWorkspaces);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkspace(@PathVariable String id) {
        return getNotImplementedResponse();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createWorkspace(@RequestBody GeneralWorkspaceRequest req) {
        return controllerWrapper(() -> workspaceService.createWorkspace(req));
    }

    @PostMapping("/{id}/allocate")
    public ResponseEntity<?> allocateUsersToWorkspace(@PathVariable String id,
                                                      @RequestBody List<String> userIds) {
        return null;
    }

}
