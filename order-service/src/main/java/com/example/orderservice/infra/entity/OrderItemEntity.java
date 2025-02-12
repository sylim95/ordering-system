package com.example.orderservice.infra.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "order_items")
@Entity
public class OrderItemEntity {

    @Id
    @Column(name = "id")
    private String id;

    /**
     * 비즈니스 관점에서 1 -> N 조회가 자주 필요하기 때문에 양방향으로 설정
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "subTotal", nullable = false)
    private BigDecimal subTotal;
}
