package com.aufgabe.engine.service;

import com.aufgabe.engine.models.Project;
import com.aufgabe.engine.models.Tag;
import com.aufgabe.engine.models.request.GeneralTagRequest;
import com.aufgabe.engine.repository.ProjectRepository;
import com.aufgabe.engine.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class TagService {

    private final ProjectRepository projectRepository;
    public Tag createTag(Project project, GeneralTagRequest generalTagRequest) {
        Tag preparedTag = Tag.builder()
                .id(UUID.randomUUID().toString())
                .title(generalTagRequest.getTitle())
                .color(generalTagRequest.getColor())
                .desc(generalTagRequest.getDesc())
                .build();

        if (Objects.isNull(project.getTags())) {
            project.setTags(new ArrayList<>());
        }

        project.getTags().add(preparedTag);
        projectRepository.save(project);

        return preparedTag;
    }

    public List<Tag> getTags(Project project) {
        return ofNullable(project.getTags())
                .orElse(Collections.emptyList());
    }

    public Tag getTagWithId(Project project, String tagId) {
        return project.getTags()
                .stream()
                .filter(t -> t.getId().equals(tagId))
                .findFirst()
                .orElseThrow();
    }

    public Tag removeTag(Project project, String tagId) {
        Tag preparedTag = this.getTagWithId(project, tagId);
        project.getTags().remove(preparedTag);

        projectRepository.save(project);

        return preparedTag;
    }

    public List<Tag> removeAllTags(Project project) {
        List<Tag> removingTags = new ArrayList<>(project.getTags());
        project.getTags().clear();

        projectRepository.save(project);
        return removingTags;
    }

}
