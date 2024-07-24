package com.cossinest.homes.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;


public class MailUtil {

    private static final String LOGO_PATH = "static/logo.png";

    public static MimeMessagePreparator buildRegistrationEmail(String recipientEmail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject("Cossinest Homes - Confirm your email address");

            String message = "<html><body style='background-color: lightgray; padding: 5px'>" +
                    "<img src='cid:logo' alt='Cossinest Homes Logo' style='width: 100px; height: auto;'><br><br>" +
                    "<p>Thank you for registering with Cossinest Homes.</p>" +

                    "<p>If you did not register with Cossinest Homes, please ignore this email.</p>" +
                    "</body></html>";

            messageHelper.setText(message, true);
            messageHelper.addInline("logo", new ClassPathResource(LOGO_PATH));
        };
    }

    public static MimeMessagePreparator buildResetPasswordEmail(String recipientEmail, String resetToken) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject("Cossinest Homes - Reset your password");

            String message = "<html><body style='background-color: lightgray; padding-left: 20px; padding-right: 20px;'>" +
                    "<img src='cid:logo' alt='Cossinest Homes Logo' style='width: 100px; height: auto;'><br><br>" +
                    "<p>You have requested to reset your password. You can use below code to reset your password:</p>" +
                    "<p><strong>Your code: " + resetToken + "</strong></p>" +
                    "<p>If you did not request a password reset, please ignore this email.</p>" +
                    "</body></html>";

            messageHelper.setText(message, true);
            messageHelper.addInline("logo", new ClassPathResource(LOGO_PATH));
        };
    }
}







