package com.smart_finance.smart_finance_be.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smart_finance.smart_finance_be.cmmn.base.Response;
import com.smart_finance.smart_finance_be.cmmn.exception.BusinessException;
import com.smart_finance.smart_finance_be.cmmn.exception.ErrorMessage;
import com.smart_finance.smart_finance_be.cmmn.utils.Constants;
import com.smart_finance.smart_finance_be.entity.OtpEntity;
import com.smart_finance.smart_finance_be.entity.UserStatus;
import com.smart_finance.smart_finance_be.entity.Users;
import com.smart_finance.smart_finance_be.payload.request.VerifyOtpRequest;
import com.smart_finance.smart_finance_be.repository.OtpRepository;
import com.smart_finance.smart_finance_be.repository.UserRepository;
import com.smart_finance.smart_finance_be.service.OtpService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService{

    private static final int OTP_LENGTH = 6;
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;

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
        entity.setUsed(Boolean.FALSE);
        otpRepository.save(entity);
    }

    @Override
    public ResponseEntity<?> verifyOtp(VerifyOtpRequest req) {

        Optional<OtpEntity> entity = otpRepository.findByEmailAndOtpAndUsedFalse(req.getEmail(),req.getOtp());

        // OTP invalid
        if(entity.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMessage(Constants.OTP_INVALID_CODE, Constants.OTP_INVALID_MES));
        }
        
        OtpEntity otpEntity = entity.get();

        // OTP expired
        if(otpEntity.getExpiredAt().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(new ErrorMessage(Constants.OTP_INVALID_CODE, Constants.OTP_INVALID_MES));
        }

        otpEntity.setUsed(Boolean.TRUE);
        otpRepository.save(otpEntity);

        Users user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new BusinessException("USER_NOT_FOUND"));

        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
        
        return ResponseEntity.ok().body(new Response().setMessage("OTP verified"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> resendOtp(String email) {

        OtpEntity entity = otpRepository.findByEmailAndExpiredAtAfter(email, LocalDateTime.now());

        // OTP still valid
        if(entity != null) {
            return ResponseEntity.badRequest().body(new ErrorMessage(Constants.OTP_STILL_VALID_CODE, Constants.OTP_STILL_VALID_MES));
        }

        String otp = this.generateOtp();
       
        saveOtp(email, otp);

        OtpEntity existingOtp = otpRepository.findByEmailAndOtpAndUsedFalse(email, otp).orElseThrow(() -> 
        new RuntimeException("OTP not found or already used"));
        
        Long expiredAt = existingOtp
            .getExpiredAt()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli();

        try {
            emailService.sendEmail(
                email,
                "OTP resend ",
                "Your OTP code is: " + otp
            );
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("UNKNOWN_EMAIL_ERROR");
        }
        return ResponseEntity.ok().body(new Response().setMessage("Send OTP to email").setData(expiredAt));
    }
}
