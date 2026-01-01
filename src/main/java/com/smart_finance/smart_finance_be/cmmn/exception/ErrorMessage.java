package com.smart_finance.smart_finance_be.cmmn.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private String statusCode;
    private String message;
}
