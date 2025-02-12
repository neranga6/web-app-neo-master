package com.webapp.neo.service;

public interface ResumeEmailService {

    void sendEmailWithOTP(String toEmail, String subject, String body) throws Exception;

    void sendAlertEmail(String toEmail, String subject, String body) throws Exception;

    void sendMessage(String toEmail, String subject, String body, String senderEmail) throws Exception;

    void sendEmailWithAttachment(String toEmail, String subject, String body, String pdfFile) throws Exception;

}
