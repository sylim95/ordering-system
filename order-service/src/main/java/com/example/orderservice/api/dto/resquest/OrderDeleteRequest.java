package com.example.orderservice.api.dto.resquest;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeleteRequest {

    /** 주문 상태 */
    @NotNull
    @Pattern(regexp = "CANCELLED|DELETED", message = "허용되지 않은 상태 값입니다.")
    private String status;
}
