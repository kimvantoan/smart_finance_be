package com.smart_finance.smart_finance_be.service.impl;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart_finance.smart_finance_be.cmmn.base.Response;
import com.smart_finance.smart_finance_be.cmmn.exception.BusinessException;
import com.smart_finance.smart_finance_be.entity.UserStatus;
import com.smart_finance.smart_finance_be.entity.Users;
import com.smart_finance.smart_finance_be.payload.request.RegisterRequest;
import com.smart_finance.smart_finance_be.repository.UserRepository;
import com.smart_finance.smart_finance_be.service.AuthService;
import com.smart_finance.smart_finance_be.service.EmailService;
import com.smart_finance.smart_finance_be.service.OtpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final EmailService emailService;

    @Override
    public ResponseEntity<?> register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new BusinessException("EMAIL_EXISTS");
        }

        Users user = new Users();
        user.setEmail(req.getEmail());
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setStatus(UserStatus.PENDING);
        userRepository.save(user);

        String otp = otpService.generateOtp();

        otpService.saveOtp(req.getEmail(), otp);

        emailService.sendEmail(req.getEmail(), "OTP", otp);

        return ResponseEntity.ok().body(new Response().setMessage("Send OTP to email"));
        
    }
}
