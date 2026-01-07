package com.smart_finance.smart_finance_be.payload.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalAmountCategory {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private Long categoryId;
}
