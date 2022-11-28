package com.aufgabe.engine.repository;

import com.aufgabe.engine.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    long count();

    List<User> findByEmailContainsIgnoreCase(String email);
    List<User> findByUsernameContainsIgnoreCase(String username);

    User findByEmailIgnoreCase(String email);
    User findByUsernameIgnoreCase(String username);

    User removeUserByEmail(String email);
    User removeUserByUsername(String username);

    default User update(User newInformation) {
        if (this.findById(newInformation.getId()).isEmpty()) {
            throw new IllegalArgumentException("Invalid id, user cannot be updated");
        }
        return this.save(newInformation);
    }

}
