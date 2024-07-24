package com.cossinest.homes.service.user;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@AllArgsConstructor
public class EmailService implements EmailServiceInterface {

    private final JavaMailSender javaMailSender;




    @Async
    public void sendEmail(MimeMessagePreparator mimeMessagePreparator) {
        javaMailSender.send(mimeMessagePreparator);
    }
}



