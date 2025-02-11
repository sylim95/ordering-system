package com.example.orderservice.api.dto.resquest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveRequest {

    /** 고객 아이디 */
    @NotNull
    private Long customerId;

    /** 주문 배송지 */
    @NotEmpty
    private String streetAddress;

    /** 주문 상품 */
    @NotEmpty
    private List<OrderSaveRequest.OrderSaveItemRequest> items;

    public record OrderSaveItemRequest(
            // 제품 아이디
            Long productId,
            // 제품 가격
            BigDecimal price,
            // 제품 수량
            Integer quantity
    ){}
}
