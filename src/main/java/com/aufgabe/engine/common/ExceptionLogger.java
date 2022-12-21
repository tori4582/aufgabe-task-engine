package com.aufgabe.engine.common;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

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

    @Data
    @Builder
    public static class ResponseException {
        private LocalDateTime dateTime;
        private String exceptionName;
        private String message;
        private String className;
        private String methodName;
        private Integer lineNumber;
        private List<String> stackTrace;

        public static ResponseException fromExceptionObject(Exception e) {
            return ResponseException.builder()
                    .message(e.getMessage())
                    .exceptionName(e.getClass().getSimpleName())
                    .dateTime(LocalDateTime.now())
                    .stackTrace(
                            Stream.of(e.getStackTrace())
                                    .map(stackTrace -> "Line %d - %s::%s".formatted(
                                            stackTrace.getLineNumber(),
                                            stackTrace.getClassName(),
                                            stackTrace.getMethodName()
                                    )).toList()
                    ).lineNumber(e.getStackTrace()[0].getLineNumber())
                    .className(e.getStackTrace()[0].getClassName())
                    .methodName(e.getStackTrace()[0].getMethodName())
                    .build();
        }
    }

}
