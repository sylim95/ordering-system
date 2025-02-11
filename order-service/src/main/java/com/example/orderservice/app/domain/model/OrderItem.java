package com.example.orderservice.app.domain.model;

import com.example.orderservice.app.port.in.OrderSaveCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    // 주문 상품 아이디
    private String id;

    // 주문 아이디
    private String orderId;

    // 제품 아이디
    private Long productId;

    // 제품 가격
    private BigDecimal price;

    // 제품 수량
    private Integer quantity;

    // 주문 금액
    private BigDecimal subTotal;

    public static OrderItem of(OrderSaveCommand.OrderItemSaveCommand command, String orderId) {
        return OrderItem.builder()
                .id(UUID.randomUUID().toString())
                .orderId(orderId)
                .productId(command.productId())
                .price(command.price())
                .quantity(command.quantity())
                .subTotal(command.price().multiply(BigDecimal.valueOf(command.quantity())))
                .build();
    }

}
