package com.aufgabe.engine.models.request;

import com.aufgabe.engine.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralUserRequest {
    private String username;
    private String email;
    private String password;
    private String displayName;
    private User.AccountProvider accountProvider;
}
