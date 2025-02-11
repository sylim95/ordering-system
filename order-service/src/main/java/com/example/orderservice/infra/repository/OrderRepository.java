package com.example.orderservice.infra.repository;


import com.example.orderservice.infra.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {

    @Query("SELECT o FROM OrderEntity o JOIN FETCH o.items ORDER BY o.createdAt DESC")
    List<OrderEntity> findAllWithItems();

    @Query("SELECT o FROM OrderEntity o JOIN FETCH o.items WHERE o.id = :id ORDER BY o.createdAt DESC")
    OrderEntity findByIdWithItems(String id);

    @Query("SELECT o FROM OrderEntity o JOIN FETCH o.items WHERE o.customerId = :customerId ORDER BY o.createdAt DESC")
    List<OrderEntity> findAllWithItemsByCustomerId(Long customerId);
}
