package com.aufgabe.engine.models.response;

import com.aufgabe.engine.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private User userInfo;
    private String jwtToken;
}
