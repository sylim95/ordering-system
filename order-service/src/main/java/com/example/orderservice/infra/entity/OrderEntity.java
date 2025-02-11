package com.example.orderservice.infra.entity;

import com.example.common.entity.BaseEntity;
import com.example.orderservice.app.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_info")
@Entity
public class OrderEntity extends BaseEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "total_quantity", nullable = false)
    private Integer totalQuantity;

    @Column(name = "street_address", nullable = false)
    private String streetAddress;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();
}
