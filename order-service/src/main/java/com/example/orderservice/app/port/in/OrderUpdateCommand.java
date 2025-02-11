package com.example.orderservice.app.port.in;

import com.example.orderservice.api.dto.resquest.OrderUpdateRequest;
import com.example.orderservice.app.domain.enums.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateCommand {
    private String id;
    private String streetAddress;
    private OrderStatus status;

    public static OrderUpdateCommand of(String id, OrderUpdateRequest request) {
        return OrderUpdateCommand.builder()
                .id(id)
                .status(OrderStatus.ofCode(request.getStatus()))
                .streetAddress(request.getStreetAddress())
                .build();
    }

}
