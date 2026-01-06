package com.smart_finance.smart_finance_be.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart_finance.smart_finance_be.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    Boolean existsByEmail(String email);

    Optional<Users> findById(Long id);
}
