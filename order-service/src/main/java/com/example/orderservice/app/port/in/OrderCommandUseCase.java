package com.example.orderservice.app.port.in;


import com.example.orderservice.app.domain.model.Order;

public interface OrderCommandUseCase {
    Order addOrder(OrderSaveCommand command);
    Order updateOrder(OrderUpdateCommand command);
    Order deleteOrder(OrderDeleteCommand command);
}
