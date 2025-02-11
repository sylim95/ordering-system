package com.example.productservice.infra.repository;


import com.example.productservice.infra.entity.ProductHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHistoryRepository extends JpaRepository<ProductHistoryEntity, Long> {
}
