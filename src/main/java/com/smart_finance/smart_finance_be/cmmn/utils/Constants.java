package com.smart_finance.smart_finance_be.cmmn.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    //resend OTP
    public static final int OTP_STILL_VALID_CODE = 410;
    public static final String OTP_STILL_VALID_MES = "OTP vẫn còn hiệu lực. Vui lòng kiểm tra email/SMS.";
}
