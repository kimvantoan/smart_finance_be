package com.smart_finance.smart_finance_be.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart_finance.smart_finance_be.entity.OtpEntity;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByEmailAndOtpAndUsedFalse(String email, String otp);
}
