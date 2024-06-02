package com.example.lesure.subscription.email;

import com.example.lesure.subscription.email.models.EmailDetailsDto;
import com.example.lesure.subscription.models.Subscription;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final String email = "";

    public void prepareAndSendEmail(Subscription subscription) {

        String email = subscription.getUser().getEmail();
        String eventName = subscription.getEvent().getDescription();
        String eventDate = subscription.getEvent().getCreatedAt().getDayOfWeek().name();
        String emailText = "Thank you for subscribing to the event: " + eventName +
                " which will occur on " + eventDate +
                ". Thank you for choosing us!";
        EmailDetailsDto details = new EmailDetailsDto()
                .setRecipient(email)
                .setSubject("Subscription Confirmation")
                .setMessage(emailText);
        sendEmail(details);
    }

    private void sendEmail(EmailDetailsDto details) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email);
        message.setTo(details.getRecipient());
        message.setSubject(details.getSubject());
        message.setText(details.getMessage());
//        mailSender.send(message);
    }
}
