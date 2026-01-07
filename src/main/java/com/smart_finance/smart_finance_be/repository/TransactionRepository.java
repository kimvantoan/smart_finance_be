package com.smart_finance.smart_finance_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart_finance.smart_finance_be.entity.Transaction;
import com.smart_finance.smart_finance_be.payload.response.TransactionProjection;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = """
            SELECT t.id, t.category_id, t.type, t.amount, t.transaction_date, t.note
            FROM transactions t
            WHERE t.user_id = :userId
                AND t.deleted = false
                AND (:type IS NULL OR t.type = :type)
                AND (:categoryId IS NULL OR t.category_id = :categoryId)
                AND (:month IS NULL OR (MONTH(t.transaction_date) = :month))
                AND (:year IS NULL OR YEAR(t.transaction_date) = :year)
            ORDER BY t.transaction_date DESC
                """, nativeQuery = true)
    List<TransactionProjection> findByUserId(
        @Param("userId") Long userId, 
        @Param("type") String type, 
        @Param("categoryId") Long categoryId, 
        @Param("year") Integer year,
        @Param("month") Integer month
    );

    @Query(value = """
            SELECT t.id, t.category_id, t.type, t.amount, t.transaction_date, t.note
            FROM transactions t
            WHERE t.user_id = :userId
                AND (:type IS NULL OR t.type = :type)
                AND (:month IS NULL OR (MONTH(t.transaction_date) = :month))
                AND (:year IS NULL OR YEAR(t.transaction_date) = :year)
                """, nativeQuery = true)
    List<TransactionProjection> findAllByUserId(
        @Param("userId") Long userId, 
        @Param("type") String type, 
        @Param("year") Integer year,
        @Param("month") Integer month
    );

    Optional<Transaction> findByUserIdAndId(Long userId, Long id);
}
