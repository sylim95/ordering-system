package com.example.orderservice.app.port.out;


import com.example.orderservice.app.domain.model.Order;

import java.util.List;

public interface LoadOrderPort {
    List<Order> loadAllOrderInfo();
    Order loadOrderInfo(String id);
    List<Order> loadAllOrderInfoByCustomerId(Long customerId);
}
