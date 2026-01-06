package com.smart_finance.smart_finance_be.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.smart_finance.smart_finance_be.cmmn.base.Response;
import com.smart_finance.smart_finance_be.cmmn.exception.ErrorMessage;
import com.smart_finance.smart_finance_be.cmmn.utils.Constants;
import com.smart_finance.smart_finance_be.entity.UserStatus;
import com.smart_finance.smart_finance_be.entity.Users;
import com.smart_finance.smart_finance_be.payload.request.LoginRequest;
import com.smart_finance.smart_finance_be.payload.request.RegisterRequest;
import com.smart_finance.smart_finance_be.repository.UserRepository;
import com.smart_finance.smart_finance_be.security.jwt.JwtUtils;
import com.smart_finance.smart_finance_be.service.AuthService;
import com.smart_finance.smart_finance_be.service.EmailService;
import com.smart_finance.smart_finance_be.service.OtpService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public ResponseEntity<?> register(RegisterRequest req) {

        // Email already exists
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(new ErrorMessage(Constants.EMAIL_EXISTS_CODE, Constants.EMAIL_EXISTS_MES));
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

      public ResponseEntity<?> login(LoginRequest req) {
        Optional<Users> optUser = userRepository.findByEmail(req.getEmail());

        // User not found
        if (optUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorMessage(401, Constants.USER_NOT_FOUND_MES));
        }

        Users user = optUser.get();

        // Password not match
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(401, Constants.USER_NOT_FOUND_MES));
        }

        // User not verified
        if(user.getStatus().equals(UserStatus.PENDING)) {
            return ResponseEntity.badRequest().body(new ErrorMessage(403, Constants.USER_NOT_VERIFIED_OTP_MES));
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtils.generateToken(userDetails.getUsername());

        return ResponseEntity.ok().body(new Response().setData(token).setMessage("Login success"));
    }
}
