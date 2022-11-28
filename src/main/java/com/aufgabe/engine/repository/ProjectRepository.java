package com.aufgabe.engine.repository;

import com.aufgabe.engine.models.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends MongoRepository<Project, String> {

    Project getProjectByName(String name);

}
