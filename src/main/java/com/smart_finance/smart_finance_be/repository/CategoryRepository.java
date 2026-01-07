package com.smart_finance.smart_finance_be.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart_finance.smart_finance_be.entity.Categories;
import com.smart_finance.smart_finance_be.payload.response.CategoryProjection;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Long> {
    @Query(value = """
            SELECT c.name, c.type, c.icon_key, c.status, c.id
            FROM categories c 
            WHERE c.user_id = :userId 
                AND c.deleted = false
                AND (:status IS NULL OR c.status = :status)
            """, nativeQuery = true)
    List<CategoryProjection> findByUserId(Long userId, @Param("status") String status);

    Optional<Categories> findById(Long id);

    Optional<Categories> findByUserIdAndId(Long userId, Long id);
}
