package com.aufgabe.engine.service;

import com.aufgabe.engine.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {

    private static final String EMAIL_SENDER = "resetpass@auf.com";

    private final JavaMailSender mailSender;

    public void issueResetPassword(User receiver, String resetIssuedLink) throws MessagingException {
        String from = "sender@gmail.com";
        String to = "hungtran6360@gmail.com";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setSubject("Reset Password for Aufgabe's Account");
        helper.setFrom(from);
        helper.setTo(to);

        helper.setText("""
                 <h1 style="padding: 1em; margin-bottom: 0px; font-size: 2em; font-family: 'Cambria'; text-align: right; background: linear-gradient(to left, #191654, #43C6AC); color: white;">
                     Aufgabe Tasks
                 </h1>
             
                 <div style="display: flex; width: 100%%; padding-top: 50px; padding-bottom: 50px;">
                     <div style="margin: auto; padding: 1em">
                         <p>Hello, <b> %s </b></p>
                         <p>This is an automatic issued email based on your request of resetting password. <em>Please do not reply to this email</em></p>
                         <p>Please click the link below and follow upcoming instructions to reset your password.</p>
                         <p>After clicking the link, this page is going to be redirected to a form which you will insert your new password</p>
             
                         <div style="border: 1px solid darkblue; background: lightblue; border-radius: 5px; padding: 25px;">
                             <a href="%s" style="content: attr(href);"> %s </a>
                         </div>
                         <br>
                         <p>If there are any further issues or supports required, please contact the <strong>Aufgabe Support Team</strong> at <em>auf@gmail.com</em>.</p>
                         <br>
                         <hr><br>
                         <p>Thanks for choosing us for your business.</p>
                         <p>Best regards,</p>
                         <br>
                         <p><strong>Aufgabe Team</strong></p>
                     </div>
                 </div>
             
                 <footer style="padding: 25px; background: #202124; color: white;">
                     <strong>Aufgabe Tasks</strong> productivity management system. 2022 &copy;
                 </footer>
                    """.formatted(
                            receiver.getDisplayName(), resetIssuedLink, resetIssuedLink
        ), true);

        mailSender.send(message);
    }
}
