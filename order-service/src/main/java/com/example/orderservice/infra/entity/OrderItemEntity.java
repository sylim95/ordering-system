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
     * 주문 아이템(N)에서 주문(1)을 조회하는 경우는 드물지만,
     * 데이터 삽입 시 `insert -> update` 흐름을 방지하기 위해 양방향 관계 설정.
     *
     * 단방향 관계로 설정할 경우 `order_id`는 연관 관계 없이 일반 컬럼으로 간주되어
     * 초기 `null`로 삽입된 후 다시 `update`가 수행되는 문제가 발생할 수 있음
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
