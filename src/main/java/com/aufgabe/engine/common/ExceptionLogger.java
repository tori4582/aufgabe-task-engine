package com.aufgabe.engine.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
public class ExceptionLogger {

    private enum ExceptionTag {
        INVALID_ACTION,
        UNEXPECTED_ISSUE
    }

    public static void logInvalidAction(Exception e) {
        logException(ExceptionTag.INVALID_ACTION, e);
    }
    public static void logUnexpectedIssue(Exception e) { logException(ExceptionTag.UNEXPECTED_ISSUE, e);}

    private static void logException(ExceptionTag tag, Exception e) {
        log.error(getExceptionTag(tag) + "%s : ".formatted(e.getClass().getSimpleName()) + e.getMessage());
    }

    private static String getExceptionTag(ExceptionTag tag) {
        return "[%s] ".formatted(tag.name());
    }

    public static ResponseEntity<String> getNotImplementedResponse() {
        return new ResponseEntity<>("Feature is not implemented yet or being implemented.", HttpStatus.NOT_IMPLEMENTED);
    }

}
