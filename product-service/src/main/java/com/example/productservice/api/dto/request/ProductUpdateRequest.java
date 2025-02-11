package com.example.productservice.api.dto.request;

import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {

    /** 제품 아이디 */
    private Long id;

    /** 제품 명 */
    private String name;

    /** 가격 */
    private BigDecimal price;

    /** 수량 */
    private Integer quantity;

    @AssertTrue(message = "최소 하나 이상의 필드를 포함해야 합니다.")
    public boolean isValid() {
        return name != null || price != null || quantity != null;
    }

    public static ProductUpdateRequest of(Long id, ProductUpdateRequest request) {
        return ProductUpdateRequest.builder()
                .id(id)
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
