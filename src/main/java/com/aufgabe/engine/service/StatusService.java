package com.aufgabe.engine.service;

import com.aufgabe.engine.models.Project;
import com.aufgabe.engine.models.Status;
import com.aufgabe.engine.repository.ProjectRepository;
import com.aufgabe.engine.repository.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;

    public Status createStatus(Project project, Status newStatus, Integer placementOrder) {
        List<String> statuses = ofNullable(project.getStatusIds())
                .orElse(new ArrayList<>());

        Status createdStatus = statusRepository.save(newStatus);

        if (Objects.isNull(placementOrder)) {
            statuses.add(createdStatus.getId());
        } else {
            statuses.add(placementOrder, createdStatus.getId());
        }

        project.setStatusIds(statuses);
        projectRepository.save(project);

        return createdStatus;
    }

    public Project updateStatuses(Project project, List<String> updatedStatuses) {
        project.setStatusIds(updatedStatuses);
        return projectRepository.save(project);
    }

    public Status removeStatus(Project project, String id) {

        if (!isProjectContainsStatus(project, id)) {
            throw new NoSuchElementException("No such status to remove");
        }

        project.getStatusIds().remove(id);
        var deletingStatus = statusRepository.findById(id).orElseThrow();
        this.updateStatuses(project, project.getStatusIds());

        statusRepository.deleteById(id);
        return deletingStatus;
    }

    public Status getStatusDetails(Project project, String id) {
        if (!isProjectContainsStatus(project, id)) {
            throw new NoSuchElementException("No such status to remove");
        }

        return statusRepository.findById(id).orElseThrow();
    }

    private boolean isProjectContainsStatus(Project project, String id) {
        var statuses = ofNullable(project.getStatusIds())
                .orElseThrow(() -> new NullPointerException("No created status list"));

        return statuses.contains(id);
    }

}
