package com.example.productservice.api.dto.request;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaveRequest {

    /** 제품 명 */
    @NotEmpty
    private String name;

    /** 가격 */
    @NotNull
    private BigDecimal price;

    /** 수량 */
    @NotNull
    private Integer quantity;
}
