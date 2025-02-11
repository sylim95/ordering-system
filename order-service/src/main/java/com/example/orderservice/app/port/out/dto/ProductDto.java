package com.example.orderservice.app.port.out.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    // 제품 아이디
    private Long id;

    // 제품 명
    private String name;

    // 제품 가격
    private BigDecimal price;

    // 제품 수량
    private Integer quantity;
}
