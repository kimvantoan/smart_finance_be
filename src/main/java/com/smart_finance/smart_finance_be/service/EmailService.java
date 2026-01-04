package com.smart_finance.smart_finance_be.service;

public interface EmailService {

    void sendEmail(String toEmail, String subject, String text);
}