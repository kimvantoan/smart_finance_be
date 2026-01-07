package com.smart_finance.smart_finance_be.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smart_finance.smart_finance_be.service.ReportService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/reports")
@AllArgsConstructor
public class ReportController {
    
    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<?> getReport(
        @RequestParam(required = true) Integer year,
        @RequestParam(required = false) Integer month,
        @RequestParam(required = true) String type
    ) {
        return reportService.getReport(year, month, type);
    }
    
}
