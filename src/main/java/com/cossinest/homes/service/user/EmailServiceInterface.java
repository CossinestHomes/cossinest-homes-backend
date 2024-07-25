package com.cossinest.homes.service.user;

import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;

public interface EmailServiceInterface {

    void sendEmail(MimeMessagePreparator mimeMessagePreparator);
}
