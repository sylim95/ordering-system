package com.example.orderservice.app.port.out;


import com.example.orderservice.app.domain.model.Order;

public interface SaveOrderPort {
    Order save(Order order);
}
