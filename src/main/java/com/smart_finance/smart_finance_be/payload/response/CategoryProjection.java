package com.smart_finance.smart_finance_be.payload.response;

import com.smart_finance.smart_finance_be.entity.CategoryStatus;
import com.smart_finance.smart_finance_be.entity.CategoryType;

public interface CategoryProjection {
    Long getId();
    String getName();
    CategoryType getType();
    String getIconKey();
    CategoryStatus getStatus();
}