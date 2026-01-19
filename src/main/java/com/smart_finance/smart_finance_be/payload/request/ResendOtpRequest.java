package com.smart_finance.smart_finance_be.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResendOtpRequest {
    @Email
    @NotBlank(message = "Email không được để trống")
    private String email;
}
