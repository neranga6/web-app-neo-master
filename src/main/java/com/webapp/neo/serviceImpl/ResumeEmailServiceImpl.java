package com.webapp.neo.serviceImpl;

import com.webapp.neo.service.ResumeEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;


@Service
@Transactional
public class ResumeEmailServiceImpl implements ResumeEmailService {


    private static final Logger logger = LoggerFactory.getLogger(ResumeEmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.properties.mail.smtp.from}")
    private String senderEmail;

    @Override
    public void sendEmailWithOTP(String toEmail, String subject, String body) throws MessagingException {
        sendMessage(toEmail, subject, body, senderEmail);
    }

    @Override
    public void sendAlertEmail(String toEmail, String subject, String body) throws MessagingException {
        sendMessage(toEmail, subject, body, toEmail);
    }

    @Async
    @Override
    public void sendMessage(String toEmail, String subject, String body, String senderEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        try {
            helper.setFrom(senderEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);  // 'true' indicates HTML content
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            logger.error("Error sending email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Override
    public void sendEmailWithAttachment(String toEmail, String subject, String body, String pdfFile) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true); // HTML content

            // Load the PDF file from the classpath
            ClassPathResource pdfResource = new ClassPathResource(pdfFile);
            if (!pdfResource.exists()) {
                throw new IOException("PDF file not found: " + pdfFile);
            }

            // Read the PDF file into a byte array
            try (InputStream inputStream = pdfResource.getInputStream()) {
                byte[] pdfBytes = inputStream.readAllBytes();

                // Create a ByteArrayResource for the attachment
                ByteArrayResource byteArrayResource = new ByteArrayResource(pdfBytes) {
                    @Override
                    public String getFilename() {
                        return "Neo_Urapola.pdf"; // You can make this dynamic if needed
                    }
                };

                // Add the attachment to the email
                helper.addAttachment(byteArrayResource.getFilename(), byteArrayResource);
            }

            // Send the email
            javaMailSender.send(mimeMessage);
        } catch (IOException e) {
            logger.error("Error reading the PDF file: {}", e.getMessage());
        } catch (MessagingException e) {
            logger.error("Error creating or sending the email with attachment: {}", e.getMessage());
        }
    }


}