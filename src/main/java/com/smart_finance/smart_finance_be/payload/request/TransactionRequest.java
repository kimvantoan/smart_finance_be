package com.smart_finance.smart_finance_be.payload.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smart_finance.smart_finance_be.entity.CategoryType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionRequest {
    
    @NotNull(message = "Category id is required")
    private Long categoryId;

    @NotNull(message = "Category type is required")
    private CategoryType type;

    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    @NotNull(message = "Transaction date is required")
    private LocalDate transactionDate;

    private String note;
}
