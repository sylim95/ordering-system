package com.example.productservice.api.dto.response;

import com.example.productservice.app.domain.model.Product;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class ProductSaveResponse {

    /** 제품 아이디 */
    private Long id;

    /** 제품 명 */
    private String name;

    /** 가격 */
    private BigDecimal price;

    /** 수량 */
    private Integer quantity;

    /** 생성 일자 */
    private LocalDateTime createdAt;

    /** 수정 일자 */
    private LocalDateTime modifiedAt;

    public static ProductSaveResponse of(final Product product) {
        return ProductSaveResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .createdAt(product.getCreatedAt())
                .modifiedAt(product.getModifiedAt())
                .build();
    }
}
