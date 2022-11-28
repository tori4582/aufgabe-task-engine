package com.aufgabe.engine.controller;

import com.aufgabe.engine.service.WorkspaceService;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class WorkspaceController {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final WorkspaceService workspaceService;

    @PostMapping("/workspace/{id}")
    public ResponseEntity<String> getWorkspace(@PathVariable String id) {
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("workspace/test")
    public ResponseEntity<String> test() {
        log.info("hello");
        log.info(mongoClient.getDatabase("aufgabe-engine-dev").listCollections().map(c -> c.toJson()).first());
        log.warn(String.valueOf(workspaceService.count()));
//        return ResponseEntity.ok().body(
//                Optional.of(mongoDatabase.listCollections()).map(c -> c.toString()).orElse(null)
//        );
        return null;
    }

}
