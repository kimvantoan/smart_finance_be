package com.smart_finance.smart_finance_be.service.impl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.smart_finance.smart_finance_be.cmmn.exception.BusinessException;
import com.smart_finance.smart_finance_be.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String text) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(text, true); 

            javaMailSender.send(message);

        } catch (MessagingException e) {
            throw new BusinessException("SEND_EMAIL_FAILED");
        }
    }
    
}
