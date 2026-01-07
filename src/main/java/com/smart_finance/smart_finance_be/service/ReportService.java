package com.smart_finance.smart_finance_be.service;

import org.springframework.http.ResponseEntity;

public interface ReportService {
    ResponseEntity<?> getReport(Integer year, Integer month, String type);
}
