package com.smart_finance.smart_finance_be.payload.request;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String email;
    private String otp;
}
