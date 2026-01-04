package com.smart_finance.smart_finance_be.entity;

import java.time.LocalDateTime;

import com.smart_finance.smart_finance_be.cmmn.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "otp")
public class OtpEntity extends BaseEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "otp", nullable = false)
    private String otp;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "used", nullable = false)
    private boolean used;
}
