package com.aufgabe.engine.repository;

import com.aufgabe.engine.models.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends MongoRepository<Status, String> {

}
