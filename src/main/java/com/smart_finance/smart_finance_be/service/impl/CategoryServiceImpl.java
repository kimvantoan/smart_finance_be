package com.smart_finance.smart_finance_be.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smart_finance.smart_finance_be.cmmn.base.Response;
import com.smart_finance.smart_finance_be.cmmn.exception.ErrorMessage;
import com.smart_finance.smart_finance_be.cmmn.utils.Constants;
import com.smart_finance.smart_finance_be.cmmn.utils.SecurityUtils;
import com.smart_finance.smart_finance_be.entity.Categories;
import com.smart_finance.smart_finance_be.payload.request.CategoryRequest;
import com.smart_finance.smart_finance_be.payload.response.CategoryProjection;
import com.smart_finance.smart_finance_be.payload.response.CategoryResponse;
import com.smart_finance.smart_finance_be.repository.CategoryRepository;
import com.smart_finance.smart_finance_be.service.CategoryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> createCategory(CategoryRequest req) {

        Long userId = SecurityUtils.getCurrentUserId();

        Categories category = modelMapper.map(req, Categories.class);

        category.setUserId(userId);

        categoryRepository.save(category);

        return ResponseEntity.ok().body(new Response().setMessage("Create category success"));

    }

    @Override
    public ResponseEntity<?> getCategories(String status, String type) {
       Long userId = SecurityUtils.getCurrentUserId();
       List<CategoryProjection> categories = categoryRepository.findByUserId(userId, status, type);

       return ResponseEntity.ok().body(new Response().setDataList(categories).setMessage("Get categories success"));
    }

    @Override
    public ResponseEntity<?> getCategoryById(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        Categories category = categoryRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Category not found")
        );

        if(category.getUserId() != userId) {
            return ResponseEntity.badRequest().body(new ErrorMessage(404,Constants.HAVE_NO_CATEGORY_MES));
        }
        CategoryResponse categoryResponse = modelMapper.map(category, CategoryResponse.class);

        return ResponseEntity.ok().body(new Response().setData(categoryResponse).setMessage("Get category success"));
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, CategoryRequest req) {
        Long userId = SecurityUtils.getCurrentUserId();

        Categories category = categoryRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Category not found")
        );

        if(userId != category.getUserId()) {
            return ResponseEntity.badRequest().body(new ErrorMessage(404,Constants.HAVE_NO_CATEGORY_MES));
        }
        
        modelMapper.map(req, category);

        categoryRepository.save(category);

        return ResponseEntity.ok().body(new Response().setMessage("Update category success"));
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        Categories category = categoryRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Category not found")
        );

        if(userId != category.getUserId()) {
            return ResponseEntity.badRequest().body(new ErrorMessage(404,Constants.HAVE_NO_CATEGORY_MES));
        }
        
        category.setDeleted(true);
        category.setDeletedAt(LocalDateTime.now());
         
        categoryRepository.save(category);

        return ResponseEntity.ok().body(new Response().setMessage("Delete category success"));
    }
    
}
