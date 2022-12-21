package com.aufgabe.engine.service;

import com.aufgabe.engine.models.Project;
import com.aufgabe.engine.models.request.GeneralProjectRequest;
import com.aufgabe.engine.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Project createProject(GeneralProjectRequest projectInformation) {

        Project exampleProject = Project.builder()
                .name(projectInformation.getName())
                .build();

        projectRepository.findOne(
                Example.of(exampleProject,
                        ExampleMatcher.matching().withIgnoreNullValues()
                )
        ).ifPresent((prj) -> {
            throw new IllegalArgumentException("Project with '%s' is already existed.");
        });

        Random random = new Random();

        Project preparedProject = Project.builder()
                .name(projectInformation.getName())
                .description(projectInformation.getDescription())
                .color(
                        ofNullable(projectInformation.getColor())
                                .orElse(null)
                )
                .build();

        return projectRepository.save(preparedProject);

    }

    public Project deleteProject(String projectId) {
        return projectRepository.findById(projectId)
                .map(project -> {
                    projectRepository.delete(project);
                    return project;
                }).orElseThrow();
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(String id) {
        return projectRepository.findById(id)
                .orElseThrow();
    }

}
