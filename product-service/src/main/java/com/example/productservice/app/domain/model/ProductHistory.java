package com.example.productservice.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductHistory {

    /** 제품 이력 아이디 */
    private Long id;

    /** 제품 아이디 */
    private Long productId;

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

    public static ProductHistory createProductHistory(Product product) {
        return ProductHistory.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
