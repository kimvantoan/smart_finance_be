package com.smart_finance.smart_finance_be.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username không được để trống")
    private String username;

    @Email
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 5, message = "Password phải nhất lớn nhat 5 ký tự")
    private String password;
    
}
