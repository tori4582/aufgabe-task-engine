package com.aufgabe.engine.service;

import com.aufgabe.engine.models.Project;
import com.aufgabe.engine.models.Workspace;
import com.aufgabe.engine.models.request.GeneralProjectRequest;
import com.aufgabe.engine.models.request.GeneralWorkspaceRequest;
import com.aufgabe.engine.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    public long count() {
        return workspaceRepository.count();
    }

    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    public Workspace getWorkspaceWithId(String workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow();
    }

    public Workspace deleteWorkspace(String workspaceId) {
        return null;
    }

    public Workspace createWorkspace(GeneralWorkspaceRequest req) {
        Workspace preparedWorkspace = Workspace.builder()
                .color(req.getColor())
                .description(req.getDescription())
                .name(req.getName())
                .projects(new ArrayList<>())
                .memberIds(new ArrayList<>())
                .build();

        return workspaceRepository.save(preparedWorkspace);
    }
}
