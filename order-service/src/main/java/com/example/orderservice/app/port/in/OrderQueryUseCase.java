package com.example.orderservice.app.port.in;


import com.example.orderservice.app.domain.model.Order;

import java.util.List;

public interface OrderQueryUseCase {
    List<Order> findAllOrderInfo(OrderSearchCommand command);
    List<Order> findAllOrderInfoByCustomerId(Long customerId, OrderSearchCommand command);
}
