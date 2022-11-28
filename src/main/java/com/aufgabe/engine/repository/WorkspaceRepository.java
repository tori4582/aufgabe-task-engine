package com.aufgabe.engine.repository;

import com.aufgabe.engine.models.User;
import com.aufgabe.engine.models.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, String> {
    long count();
//    boolean addUser(User user);
}
