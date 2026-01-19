package com.smart_finance.smart_finance_be.cmmn.exception;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
