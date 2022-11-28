package com.aufgabe.engine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ResetPasswordCredentialsService {

    private final Map<String, String> resetRequestCredentials;

    public String createResetCredentials(String username) {
        String uuid = UUID.randomUUID().toString();

        this.resetRequestCredentials.put(username, uuid);

        return uuid;
    }

    public String acceptResetCredential(String credential) {

        Map.Entry<String, String> issuedCredential = this.resetRequestCredentials.entrySet()
                .stream()
                .filter((Map.Entry<String, String> entry) -> entry.getValue().equals(credential))
                .findFirst()
                .orElseThrow(() -> {
                    throw new NoSuchElementException("Account has not been issued any credential yet.");
                });


        if (!issuedCredential.getValue().equals(credential)) {
            throw new IllegalArgumentException("Invalid or unmatched credential");
        }

        return issuedCredential.getKey();
    }

    public void eliminateCredentials(String username) {
        this.resetRequestCredentials.remove(username);
    }

}
