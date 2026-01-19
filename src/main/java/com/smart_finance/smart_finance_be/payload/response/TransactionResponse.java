package com.smart_finance.smart_finance_be.payload.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smart_finance.smart_finance_be.entity.CategoryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long id;
    private Long categoryId;
    private CategoryType type;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private String note;

}
