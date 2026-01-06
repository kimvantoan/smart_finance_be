package com.smart_finance.smart_finance_be.service;

import org.springframework.http.ResponseEntity;

import com.smart_finance.smart_finance_be.payload.request.LoginRequest;
import com.smart_finance.smart_finance_be.payload.request.RegisterRequest;

public interface AuthService {
    ResponseEntity<?>register(RegisterRequest registerRequest);

    ResponseEntity<?> login(LoginRequest req);
}
