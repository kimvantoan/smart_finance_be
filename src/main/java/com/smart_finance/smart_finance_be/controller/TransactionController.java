package com.smart_finance.smart_finance_be.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smart_finance.smart_finance_be.payload.request.TransactionRequest;
import com.smart_finance.smart_finance_be.service.TransactionService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {
    
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<?> getTransactions(
        @RequestParam(required = false) String type,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) Integer year,
        @RequestParam(required = false) Integer month
    ) {
        return transactionService.getTransactions(type, categoryId, year, month);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody TransactionRequest req) {
        return transactionService.createTransaction(req);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody TransactionRequest req,@PathVariable Long id) {
        return transactionService.updateTransaction(id, req);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return transactionService.deleteTransaction(id);
    }
    
}
