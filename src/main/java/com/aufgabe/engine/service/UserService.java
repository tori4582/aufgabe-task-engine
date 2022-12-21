package com.aufgabe.engine.service;

import com.aufgabe.engine.models.User;
import com.aufgabe.engine.models.request.ForgetPasswordRequest;
import com.aufgabe.engine.models.request.GeneralUserRequest;
import com.aufgabe.engine.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JavaMailSender mailSender;

    private final UserRepository userRepository;
    private final MailService mailService;
    private final ResetPasswordCredentialsService resetPasswordCredentialsService;

    @Value("ple")
    private final String hostUrl;

    public List<User> search(String identifier) {
        return (identifier.contains("@"))
                ? userRepository.findByEmailContainsIgnoreCase(identifier)
                : userRepository.findByUsernameContainsIgnoreCase(identifier);
    }

    public User getUser(String identifier) {
        return (identifier.contains("@"))
                ? userRepository.findByEmailIgnoreCase(identifier)
                : userRepository.findByUsernameIgnoreCase(identifier);
    }


    public User login(String username, String password) {
        User user = this.getUser(username);
        return (user.isSameIdentifier(username)
                && user.getPassword().equals(password))
                    ? user : null;
    }

    public void issueResetPassword(User receiver) throws MessagingException {
        String credential = this.resetPasswordCredentialsService.createResetCredentials(receiver.getUsername());
        String resetLink = "http://localhost:8080/aufweb/reset-pass.html?resetCredential=" + credential;
        mailService.issueResetPassword(receiver, resetLink);
    }

    public String acceptResetCredential(String credential) {
        return this.resetPasswordCredentialsService.acceptResetCredential(credential);
    }

    public void issueWorkspaceAllocation(User receiver) throws MessagingException {
        String
    }


    public User signUp(GeneralUserRequest request) {

        if (this.getUser(request.getEmail()) != null) {
            throw new IllegalArgumentException(
                    "User with gmail '%s' is already existed".formatted(request.getEmail())
            );
        }

        return userRepository.save(User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .accountProvider(request.getAccountProvider())
                .displayName(request.getDisplayName())
                .privilege(User.MemberPrivilege.MEMBER)
                .build()
        );
    }

    /* Test only */
    public long removeAllUsers() {
        long quantity = userRepository.count();
        userRepository.deleteAll();
        return quantity;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User newInformation) {
        return userRepository.update(newInformation);
    }

}
