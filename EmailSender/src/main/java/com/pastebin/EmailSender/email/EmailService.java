package com.pastebin.EmailSender.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailProperties mailProperties;

    public void sendEmail(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailProperties.getUsername());
        message.setTo(emailMessage.getRecipients().toArray(new String[0]));
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getText());

        mailSender.send(message);
    }
}
