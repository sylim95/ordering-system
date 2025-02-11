package com.example.orderservice.app.domain.event;

import com.example.orderservice.app.port.out.dto.ProductDto;

import java.util.List;

public record OrderCreatedEvent(String orderId, List<ProductDto> products) {
}
