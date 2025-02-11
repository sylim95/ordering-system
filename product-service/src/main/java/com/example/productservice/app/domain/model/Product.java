package com.example.productservice.app.domain.model;


import com.example.productservice.app.port.in.ProductSaveCommand;
import com.example.productservice.app.port.in.ProductUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

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

    /** 버전 */
    private Integer version;

    public static Product createProduct(ProductSaveCommand command) {
        return Product.builder()
                .name(command.getName())
                .price(command.getPrice())
                .quantity(command.getQuantity())
                .build();
    }

    /**
     * 제품 정보 수정
     * 각 필드를 `null` 체크하여 요청된 값만 업데이트
     * @param command
     */
    public void updateProduct(ProductUpdateCommand command) {
        this.id = command.getId();
        if(command.getName() != null) {
            this.name = command.getName();
        }

        if(command.getPrice() != null) {
            this.price = command.getPrice();
        }

        if(command.getQuantity() != null) {
            this.quantity = command.getQuantity();
        }
    }

    /**
     * 제품 재고 감소
     * - 현재 재고가 요청된 수량보다 적으면 예외 발생
     * @param quantity 차감할 재고 수량
     * @throws IllegalArgumentException 재고 부족 시 예외 발생
     */
    public void updateStock(int quantity) {
        if (this.quantity < quantity) {
            throw new IllegalArgumentException("재고가 부족합니다.");
        }
        this.quantity -= quantity;
    }
}
