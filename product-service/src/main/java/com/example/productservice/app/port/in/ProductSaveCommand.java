package com.example.productservice.app.port.in;

import com.example.productservice.api.dto.request.ProductSaveRequest;
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
public class ProductSaveCommand {

    /** 제품 명 */
    private String name;

    /** 가격 */
    private BigDecimal price;

    /** 수량 */
    private Integer quantity;

    public static ProductSaveCommand of(ProductSaveRequest request) {
        return ProductSaveCommand.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
    }
}
