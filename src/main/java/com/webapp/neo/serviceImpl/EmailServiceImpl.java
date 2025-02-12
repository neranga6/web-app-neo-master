package com.webapp.neo.serviceImpl;

import com.webapp.neo.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final EmailService emailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.emailSender = (to, subject, content) -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        };
    }
    @Async
    public void sendEmail(String to, String subject, String content) {
        emailSender.sendEmail(to, subject, content);
    }


}
