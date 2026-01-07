package com.smart_finance.smart_finance_be.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.smart_finance.smart_finance_be.cmmn.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Column(name = "amount",nullable = false)
    private BigDecimal amount;

    @Column(name = "transaction_date",nullable = false)
    private LocalDate transactionDate;

    @Column(name = "note")
    private String note;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; 

}
