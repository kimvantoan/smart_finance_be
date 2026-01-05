package com.smart_finance.smart_finance_be.service;

import org.springframework.http.ResponseEntity;

import com.smart_finance.smart_finance_be.payload.request.VerifyOtpRequest;

public interface OtpService {

    String generateOtp();

    void saveOtp(String email, String otp);

    ResponseEntity<?> verifyOtp(VerifyOtpRequest req);
    
    ResponseEntity<?> resendOtp(String email);
}
