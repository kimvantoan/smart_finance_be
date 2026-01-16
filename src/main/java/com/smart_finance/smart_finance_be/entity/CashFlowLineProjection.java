package com.smart_finance.smart_finance_be.entity;

import java.math.BigDecimal;

public interface CashFlowLineProjection {
    Integer getTime();    
    BigDecimal getIncome();
    BigDecimal getExpense();
}