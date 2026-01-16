package com.smart_finance.smart_finance_be.entity;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CashFlowLineDTO {
    private Integer time; // day or month
    private BigDecimal income;
    private BigDecimal expense;
}