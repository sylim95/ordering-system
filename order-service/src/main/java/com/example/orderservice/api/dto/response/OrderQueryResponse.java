package com.example.orderservice.api.dto.response;


import com.example.orderservice.app.domain.enums.OrderStatus;
import com.example.orderservice.app.domain.model.Order;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class OrderQueryResponse {

    /** 주문 아이디 */
    private String id;

    /** 고객 아이디 */
    private Long customerId;

    /** 총 주문 금액 */
    private BigDecimal totalPrice;

    /** 총 주문 수량 */
    private Integer totalQuantity;

    /** 주문 배송지 */
    private String streetAddress;

    /** 주문 상태 */
    private OrderStatus status;

    /** 생성 일자 */
    private LocalDateTime createdAt;

    /** 주문 상품 */
    private List<OrderQueryItemResponse> items;

    public record OrderQueryItemResponse(
            // 주문 상품 아이디
            String id,
            // 제품 아이디
            Long productId,
            // 제품 가격
            BigDecimal price,
            // 제품 수량
            Integer quantity,
            // 주문 금액
            BigDecimal subTotal
    ){}

    public static OrderQueryResponse of(final Order order) {
        return OrderQueryResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .totalPrice(order.getTotalPrice())
                .totalQuantity(order.getTotalQuantity())
                .streetAddress(order.getStreetAddress())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .items(convertOrderItems(order))
                .build();
    }

    private static List<OrderQueryItemResponse> convertOrderItems(Order order) {
        return order.getItems().stream()
                .map(o -> new OrderQueryItemResponse(
                        o.getId(),
                        o.getProductId(),
                        o.getPrice(),
                        o.getQuantity(),
                        o.getSubTotal()
                )).toList();
    }
}
