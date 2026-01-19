package com.smart_finance.smart_finance_be.service;

import org.springframework.http.ResponseEntity;

import com.smart_finance.smart_finance_be.payload.request.TransactionRequest;

public interface TransactionService {
    
    ResponseEntity<?> createTransaction(TransactionRequest req);

    ResponseEntity<?> getTransactions( String type, Long categoryId, Integer year, Integer month);

    ResponseEntity<?> getTransactionById(Long id);

    ResponseEntity<?> updateTransaction(Long id, TransactionRequest req);

    ResponseEntity<?> deleteTransaction(Long id);
}
