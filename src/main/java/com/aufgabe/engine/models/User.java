package com.aufgabe.engine.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Builder
@Document
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String title;
    private String displayName;
    private String imageUrl;
    private MemberPrivilege privilege;
    private AccountProvider accountProvider;
    private String password;
    private List<String> assignedWorkplaces;

    public boolean isSameIdentifier(String identifier) {
        return identifier.equalsIgnoreCase(
                (identifier.contains("@")) ? this.email : this.username
        );
    }

    public enum MemberPrivilege {
        MANAGER,
        MEMBER,
        READONLY
    }

    public enum AccountProvider {
        GOOGLE,
        FACEBOOK,
        AUF
    }

}
