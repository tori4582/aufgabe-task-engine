package com.aufgabe.engine.controller;

import com.aufgabe.engine.common.JwtUtil;
import com.aufgabe.engine.models.User;
import com.aufgabe.engine.models.request.ForgetPasswordRequest;
import com.aufgabe.engine.models.request.GeneralUserRequest;
import com.aufgabe.engine.models.response.LoginResponse;
import com.aufgabe.engine.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;

import static com.aufgabe.engine.common.ApplicationUtils.controllerWrapper;
import static com.aufgabe.engine.common.ExceptionLogger.logInvalidAction;
import static com.aufgabe.engine.common.ExceptionLogger.logUnexpectedIssue;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody @NonNull GeneralUserRequest req) {
        try {
            return ResponseEntity.ok().body(userService.signUp(req));
        } catch (Exception e) {
            logInvalidAction(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody GeneralUserRequest req) {

        return Optional.ofNullable(userService.login(req.getUsername(), req.getPassword()))
                .map(userInfo -> ResponseEntity.ok(
                        LoginResponse.builder()
                                .userInfo(userInfo)
                                .jwtToken(JwtUtil.generateJwtToken(userInfo))
                                .build()
                     )
                ).orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getUser(@RequestParam @NonNull String identifier) {
        return Optional.ofNullable(userService.search(identifier))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        return controllerWrapper(userService::getAllUsers);
    }

    @PostMapping("/issue-forget-pass-mail")
    public ResponseEntity<?> issueForgetPasswordMail(@RequestBody String identifier) {
        return Optional.ofNullable(userService.getUser(identifier))
                .map(user -> {
                    try {
                        userService.issueResetPassword(user);
                    } catch (MessagingException e) {
                        logUnexpectedIssue(e);
                    }
                    return ResponseEntity.ok("The reset email has been issued and send");
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/reset-pass")
    public ResponseEntity<?> forgetPassword(@RequestParam @NonNull String resetCredential,
                                            @RequestBody ForgetPasswordRequest req) {
        User user;

        try {
            user = userService.getUser(
                userService.acceptResetCredential(resetCredential)
            );
        } catch (Exception e) {
            logInvalidAction(e);
            return ResponseEntity.badRequest().body("Invalid credentials");
        }

        user.setPassword(req.getNewPassword());
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping
    public ResponseEntity<String> removeAllUsers() {
        String message = "Removed %d users".formatted(userService.removeAllUsers());
        log.warn("[DANGEROUS_ACTION] " + message);
        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @NonNull User newInformation) {
        try {
            return ResponseEntity.ok().body(userService.updateUser(newInformation));
        } catch (Exception e) {
            logInvalidAction(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
