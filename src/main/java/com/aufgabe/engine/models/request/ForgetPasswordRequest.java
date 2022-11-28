package com.aufgabe.engine.models.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@Builder
public class ForgetPasswordRequest {

    private @NonNull String newPassword;

}
