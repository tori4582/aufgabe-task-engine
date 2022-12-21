package com.aufgabe.engine.common;

import org.springframework.http.ResponseEntity;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

import static com.aufgabe.engine.common.ExceptionLogger.logInvalidAction;

public class ApplicationUtils {

    public static ResponseEntity<?> controllerWrapper(Supplier controllerExecution) {
        try {
            return ResponseEntity.ok(controllerExecution.get());
        } catch (Exception e) {
            logInvalidAction(e);
            return switchExceptionsResponse(e);
        }
    }

    private static ResponseEntity<?> switchExceptionsResponse(Exception e) {
        if (e instanceof NoSuchElementException) {
            return ResponseEntity.notFound().build();
        }
        if (e instanceof IllegalArgumentException) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.internalServerError().body(ExceptionLogger.ResponseException.fromExceptionObject(e));
    }

}
