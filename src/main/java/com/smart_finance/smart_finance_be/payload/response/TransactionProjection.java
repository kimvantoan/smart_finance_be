package com.smart_finance.smart_finance_be.payload.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.smart_finance.smart_finance_be.entity.CategoryType;

public interface TransactionProjection {
    Long getId();
    Long getCategoryId();
    CategoryType getType();
    BigDecimal getAmount();
    LocalDate getTransactionDate();
    String getNote();   

}
