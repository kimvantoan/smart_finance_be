package com.smart_finance.smart_finance_be.service.impl;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.smart_finance.smart_finance_be.cmmn.base.Response;
import com.smart_finance.smart_finance_be.cmmn.utils.SecurityUtils;
import com.smart_finance.smart_finance_be.entity.CashFlowLineDTO;
import com.smart_finance.smart_finance_be.entity.CashFlowLineProjection;
import com.smart_finance.smart_finance_be.entity.CategoryType;
import com.smart_finance.smart_finance_be.payload.response.ReportResponse;
import com.smart_finance.smart_finance_be.payload.response.TotalAmountCategory;
import com.smart_finance.smart_finance_be.payload.response.TransactionProjection;
import com.smart_finance.smart_finance_be.repository.TransactionRepository;
import com.smart_finance.smart_finance_be.service.ReportService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<?> getReport(Integer year, Integer month, String type) {
        Long userId = SecurityUtils.getCurrentUserId();

        ReportResponse reportResponse = new ReportResponse();

        // calculate total income
        reportResponse.setTotalIncome(calculateTotalIncome(transactionRepository.findAllByUserId(userId, "INCOME", year, month)));

        // calculate total expense
        reportResponse.setTotalExpense(calculateTotalExpense(transactionRepository.findAllByUserId(userId, "EXPENSE", year, month)));

        // calculate total balance
        reportResponse.setTotalBalance(reportResponse.getTotalIncome().subtract(reportResponse.getTotalExpense()));

        // calculate total amount by category
        reportResponse.setTotalAmountCategoryList(calculateTotalAmountByCategory(year, month, type, userId));

        return ResponseEntity.ok().body(new Response().setData(reportResponse).setMessage("Get report success"));

    }

    @Override
    public ResponseEntity<?> getDataLineChart(Integer year, Integer month) {

        Long userId = SecurityUtils.getCurrentUserId();
    
        if (month != null && month != 0) {
            var raw = transactionRepository.getCashFlowByDay(userId, year, month);
    
            return ResponseEntity.ok(
                fillFullDays(raw, year, month)
            );
        }
    
        var raw = transactionRepository.getCashFlowByMonth(userId, year);
    
        return ResponseEntity.ok(
            fillFullMonths(raw)
        );
    }
    
    
    private BigDecimal calculateTotalIncome(List<TransactionProjection> transactions) {

        BigDecimal totalAmount = 
            transactions.stream().map(TransactionProjection::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAmount;
    }

    private BigDecimal calculateTotalExpense(List<TransactionProjection> transactions) {

        BigDecimal totalAmount = 
            transactions.stream().map(TransactionProjection::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return totalAmount;
    }
    
    private List<TotalAmountCategory> calculateTotalAmountByCategory(
        Integer year, Integer month, String type, Long userId
    ) {
        List<TransactionProjection> transactions =
                transactionRepository.findAllByUserId(userId, type, year, month);

        Map<Long, BigDecimal> totalByCategory =
                transactions.stream()
                    .filter(t -> t.getCategoryId() != null && t.getAmount() != null)
                    .collect(Collectors.groupingBy(
                        TransactionProjection::getCategoryId,
                        Collectors.mapping(
                            TransactionProjection::getAmount,
                            Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                    ));

        return totalByCategory.entrySet().stream()
                .map(e -> new TotalAmountCategory(e.getValue(), e.getKey()))
                .toList();
    }


    private List<CashFlowLineDTO> fillFullDays(
        List<CashFlowLineProjection> rawData,
        int year,
        int month
) {
    Map<Integer, CashFlowLineProjection> map = rawData.stream()
        .collect(Collectors.toMap(
            CashFlowLineProjection::getTime,
            i -> i
        ));

    int daysInMonth = YearMonth.of(year, month).lengthOfMonth();

    List<CashFlowLineDTO> result = new ArrayList<>();

    for (int day = 1; day <= daysInMonth; day++) {
        if (map.containsKey(day)) {
            var d = map.get(day);
            result.add(new CashFlowLineDTO(
                day,
                d.getIncome(),
                d.getExpense()
            ));
        } else {
            result.add(new CashFlowLineDTO(
                day,
                BigDecimal.ZERO,
                BigDecimal.ZERO
            ));
        }
    }

    return result;
}

    private List<CashFlowLineDTO> fillFullMonths(
        List<CashFlowLineProjection> rawData
    ) {
    Map<Integer, CashFlowLineProjection> map = rawData.stream()
        .collect(Collectors.toMap(
            CashFlowLineProjection::getTime,
            i -> i
        ));

    List<CashFlowLineDTO> result = new ArrayList<>();

    for (int m = 1; m <= 12; m++) {
        if (map.containsKey(m)) {
            var d = map.get(m);
            result.add(new CashFlowLineDTO(
                m,
                d.getIncome(),
                d.getExpense()
            ));
        } else {
            result.add(new CashFlowLineDTO(
                m,
                BigDecimal.ZERO,
                BigDecimal.ZERO
            ));
        }
    }

    return result;
    }

}
