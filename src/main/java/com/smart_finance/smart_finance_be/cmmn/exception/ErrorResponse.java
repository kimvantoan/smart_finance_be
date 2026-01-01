package com.smart_finance.smart_finance_be.cmmn.exception;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    private HttpStatus status;
    private String message;
}
