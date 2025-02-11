package com.example.orderservice.api.dto.resquest;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
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
public class OrderUpdateRequest {

    /** 주문 배송지 */
    private String streetAddress;

    /** 주문 상태
     * - 취소 또는 삭제 상태로 변경 불가
     * */
    @Pattern(regexp = "PAID|APPROVED", message = "허용되지 않은 상태 값입니다.")
    private String status;

    /** 주문 상품 */
    private List<OrderUpdateRequest.OrderUpdateItemRequest> items;

    public record OrderUpdateItemRequest(
            // 주문 상품 아이디
            String id,
            // 제품 아이디
            Long productId,
            // 제품 가격
            BigDecimal price,
            // 제품 수량
            Integer quantity
    ){}

    @AssertTrue(message = "최소 하나 이상의 필드를 포함해야 합니다.")
    public boolean isValid() {
        return streetAddress != null || status != null;
    }
}
