package com.smart_finance.smart_finance_be.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.smart_finance.smart_finance_be.cmmn.base.Response;
import com.smart_finance.smart_finance_be.cmmn.exception.ErrorMessage;
import com.smart_finance.smart_finance_be.cmmn.utils.Constants;
import com.smart_finance.smart_finance_be.cmmn.utils.SecurityUtils;
import com.smart_finance.smart_finance_be.entity.Categories;
import com.smart_finance.smart_finance_be.entity.Transaction;
import com.smart_finance.smart_finance_be.payload.request.TransactionRequest;
import com.smart_finance.smart_finance_be.payload.response.TransactionProjection;
import com.smart_finance.smart_finance_be.payload.response.TransactionResponse;
import com.smart_finance.smart_finance_be.repository.CategoryRepository;
import com.smart_finance.smart_finance_be.repository.TransactionRepository;
import com.smart_finance.smart_finance_be.service.TransactionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> createTransaction(TransactionRequest req) {
        Long userId = SecurityUtils.getCurrentUserId();

        Optional<Categories> category = categoryRepository.findByUserIdAndId(userId,req.getCategoryId());
        
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(404,Constants.HAVE_NO_CATEGORY_MES));
        }

        Transaction transaction = modelMapper.map(req, Transaction.class);

        transaction.setUserId(userId);

        transactionRepository.save(transaction);

        return ResponseEntity.ok().body(new Response().setMessage("Create transaction success"));

    }

    @Override
    public ResponseEntity<?> getTransactions( String type, Long categoryId, Integer year, Integer month) {
       Long userId = SecurityUtils.getCurrentUserId();
       System.out.println("month: " + month);

       List<TransactionProjection> transactions = transactionRepository.findByUserId(userId , type, categoryId, year, month);

       return ResponseEntity.ok().body(new Response().setDataList(transactions).setMessage("Get transactions success"));

    }

    @Override
    public ResponseEntity<?> getTransactionById(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        Optional<Transaction> transaction = transactionRepository.findByUserIdAndId(userId, id);

        if (transaction.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(404,Constants.TRANSACTION_NOT_FOUND_MES));
        }

        TransactionResponse response = modelMapper.map(transaction.get(), TransactionResponse.class);
        
        return ResponseEntity.ok().body(new Response().setData(response).setMessage("Get transaction success"));
    }

    @Override
    public ResponseEntity<?> updateTransaction(Long id, TransactionRequest req) {
        Long userId = SecurityUtils.getCurrentUserId();

        Optional<Categories> category = categoryRepository.findByUserIdAndId(userId,req.getCategoryId());
        
        //check exist category
        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(404,Constants.HAVE_NO_CATEGORY_MES));
        }

        Optional<Transaction> transaction = transactionRepository.findByUserIdAndId(userId, id);

        //check exist transaction
        if (transaction.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(404,Constants.TRANSACTION_NOT_FOUND_MES));
        }

        Transaction transactionUpdate = transaction.get();

        modelMapper.map(req, transactionUpdate);

        transactionRepository.save(transactionUpdate);

        return ResponseEntity.ok().body(new Response().setMessage("Update transaction success"));
    }

    @Override
    public ResponseEntity<?> deleteTransaction(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        Transaction transaction = transactionRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Transaction not found")
        );

        if(userId != transaction.getUserId()) {
            return ResponseEntity.badRequest().body(new ErrorMessage(404,Constants.TRANSACTION_NOT_FOUND_MES));
        }
        
        transaction.setDeleted(true);
        transaction.setDeletedAt(LocalDateTime.now());
         
        transactionRepository.save(transaction);

        return ResponseEntity.ok().body(new Response().setMessage("Delete transaction success"));
    }
    
}
