package com.smart_finance.smart_finance_be.service;

import org.springframework.http.ResponseEntity;

import com.smart_finance.smart_finance_be.payload.request.CategoryRequest;

public interface CategoryService {
    ResponseEntity<?> createCategory(CategoryRequest req);

    ResponseEntity<?> getCategories(String status);

    ResponseEntity<?> getCategoryById(Long id);

    ResponseEntity<?> updateCategory(Long id, CategoryRequest req);

    ResponseEntity<?> deleteCategory(Long id);
}
