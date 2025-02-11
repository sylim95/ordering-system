package com.example.orderservice.app.port.in;


import com.example.orderservice.api.dto.resquest.OrderDeleteRequest;
import com.example.orderservice.app.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDeleteCommand {
    private String id;
    private OrderStatus status;

    public static OrderDeleteCommand of(String id, OrderDeleteRequest request) {
        return OrderDeleteCommand.builder()
                .id(id)
                .status(OrderStatus.ofCode(request.getStatus()))
                .build();
    }
}
