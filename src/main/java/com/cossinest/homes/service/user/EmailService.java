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

//    public void sendEmail(String email,String subject,String text){
//        SimpleMailMessage message=new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject(subject);
//        message.setText(text);
//        javaMailSender.send(message);
//    }


    public void sendEmail(MimeMessagePreparator mimeMessagePreparator) {
        javaMailSender.send(mimeMessagePreparator);
    }
}


/*
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.security</groupId>
<artifactId>spring-security-test</artifactId>
<scope>test</scope>
</dependency>
*/
