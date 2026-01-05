package com.smart_finance.smart_finance_be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_finance.smart_finance_be.payload.request.RegisterRequest;
import com.smart_finance.smart_finance_be.payload.request.ResendOtpRequest;
import com.smart_finance.smart_finance_be.payload.request.VerifyOtpRequest;
import com.smart_finance.smart_finance_be.service.AuthService;
import com.smart_finance.smart_finance_be.service.OtpService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    private final OtpService otpService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@Valid @RequestBody VerifyOtpRequest req) {
        return otpService.verifyOtp(req);
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@Valid @RequestBody ResendOtpRequest req) {
        return otpService.resendOtp(req.getEmail());
    }
    
}
