package com.smart_finance.smart_finance_be.entity;

import java.time.LocalDateTime;

import com.smart_finance.smart_finance_be.cmmn.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
public class Categories extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CategoryType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CategoryStatus status;
    
    @Column(name = "icon_key", nullable = false)
    private String iconKey;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt; 

    public Categories(Long userId,
        String name,
        CategoryType type,
        CategoryStatus status,
        String iconKey) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.status = status;
        this.iconKey = iconKey;
        this.deleted = false;
    }
}
