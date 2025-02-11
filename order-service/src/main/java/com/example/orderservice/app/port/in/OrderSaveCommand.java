package com.example.orderservice.app.port.in;

import com.example.orderservice.api.dto.resquest.OrderSaveRequest;
import com.example.orderservice.app.domain.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveCommand {
    private Long customerId;
    private String streetAddress;
    private OrderStatus status;
    private List<OrderItemSaveCommand> items;

    public record OrderItemSaveCommand(
            Long productId,
            BigDecimal price,
            Integer quantity
    ){}

    public static OrderSaveCommand of(OrderSaveRequest request) {
        var orderItems = convertOrderSaveItem(request);
        return OrderSaveCommand.builder()
                .customerId(request.getCustomerId())
                .streetAddress(request.getStreetAddress())
                .status(OrderStatus.APPROVED)
                .items(orderItems)
                .build();
    }

    private static List<OrderItemSaveCommand> convertOrderSaveItem(OrderSaveRequest request) {
        return request.getItems().stream()
                .map(o -> new OrderSaveCommand.OrderItemSaveCommand(
                        o.productId(),
                        o.price(),
                        o.quantity()
                )).toList();

    }
}
