package com.smart_finance.smart_finance_be.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePwRequest {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
