package com.smart_finance.smart_finance_be.payload.response;

import com.smart_finance.smart_finance_be.entity.CategoryStatus;
import com.smart_finance.smart_finance_be.entity.CategoryType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private CategoryType type;
    private String iconKey;
    private CategoryStatus status;
}
