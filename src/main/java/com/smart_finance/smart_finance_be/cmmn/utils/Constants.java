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

    //Register
    public static final int EMAIL_EXISTS_CODE = 409;
    public static final String EMAIL_EXISTS_MES = "Email đã được đăng ký";
    
    //Login
    public static final String USER_NOT_FOUND_MES = "Tài khoản hoặc mật khẩu không chính xác";
    public static final String USER_NOT_VERIFIED_OTP_MES = "User chưa verify otp";

    //Verify OTP
    public static final String OTP_INVALID_MES = "OTP không chính xác";
    public static final int OTP_INVALID_CODE = 411;

    //No authorization
    public static final String HAVE_NO_CATEGORY_MES = "Bạn không có danh mục này";

    //Transaction
    public static final String TRANSACTION_NOT_FOUND_MES = "Không tìm thấy giao dịch";
}
