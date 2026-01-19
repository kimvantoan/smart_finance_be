package com.smart_finance.smart_finance_be.payload.request;

import com.smart_finance.smart_finance_be.entity.CategoryStatus;
import com.smart_finance.smart_finance_be.entity.CategoryType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 30, message = "Category name must be less than 30 characters")
    private String name;

    @NotNull(message = "Category type is required")
    private CategoryType type;

    @NotBlank(message = "Icon key is required")
    private String iconKey;

    @NotNull(message = "Category status is required")
    private CategoryStatus status;
}
