package com.example.productservice.app.port.in;

import com.example.productservice.api.dto.request.ProductUpdateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateCommand {

    /** 제품 아이디 */
    private Long id;

    /** 제품 명 */
    private String name;

    /** 가격 */
    private BigDecimal price;

    /** 수량 */
    private Integer quantity;

    public static ProductUpdateCommand of(ProductUpdateRequest request) {
        return ProductUpdateCommand.builder()
                .id(request.getId())
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
