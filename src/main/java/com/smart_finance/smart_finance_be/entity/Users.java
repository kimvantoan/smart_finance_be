package com.smart_finance.smart_finance_be.entity;

import java.util.Currency;

import com.smart_finance.smart_finance_be.cmmn.base.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class Users extends BaseEntity {

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "password", nullable = false)
    private String password;

    @PrePersist
    public void prePersist() {
        if (currency == null) {
            currency = Currency.getInstance("VND");
        }
    }
}
 
