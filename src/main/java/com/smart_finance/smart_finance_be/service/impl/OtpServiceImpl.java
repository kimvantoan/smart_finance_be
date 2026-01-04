package com.smart_finance.smart_finance_be.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smart_finance.smart_finance_be.cmmn.base.Response;
import com.smart_finance.smart_finance_be.cmmn.exception.BusinessException;
import com.smart_finance.smart_finance_be.entity.OtpEntity;
import com.smart_finance.smart_finance_be.entity.UserStatus;
import com.smart_finance.smart_finance_be.entity.Users;
import com.smart_finance.smart_finance_be.payload.request.VerifyOtpRequest;
import com.smart_finance.smart_finance_be.repository.OtpRepository;
import com.smart_finance.smart_finance_be.repository.UserRepository;
import com.smart_finance.smart_finance_be.service.OtpService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService{

    private static final int OTP_LENGTH = 6;
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Value("${otp.expired-minutes}")
    private int expiredMinutes;

    @Override
    public String generateOtp() {

        int min = (int) Math.pow(10, OTP_LENGTH - 1);
        int max = (int) Math.pow(10, OTP_LENGTH) - 1;

        int otp = ThreadLocalRandom.current().nextInt(min, max + 1);
        return String.valueOf(otp);
    }

    @Override
    public void saveOtp(String email, String otp) {
        OtpEntity entity = new OtpEntity();
        entity.setEmail(email);
        entity.setOtp(otp);
        entity.setExpiredAt(LocalDateTime.now().plusMinutes(expiredMinutes));
        entity.setUsed(false);
        otpRepository.save(entity);
    }

    @Override
    public ResponseEntity<?> verifyOtp(VerifyOtpRequest req) {

        OtpEntity entity = otpRepository.findByEmailAndOtpAndUsedFalse(req.getEmail(),req.getOtp())
                .orElseThrow(() -> new BusinessException("OTP_INVALID"));
        
        if(entity.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException("OTP_EXPIRED");
        }

        entity.setUsed(true);
        otpRepository.save(entity);

        Users user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND"));

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        
        return ResponseEntity.ok().body(new Response().setMessage("OTP verified"));
    }
}
