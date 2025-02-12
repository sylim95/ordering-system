package com.example.orderservice.app.domain.service;

import com.example.orderservice.app.domain.enums.OrderStatus;
import com.example.orderservice.app.domain.model.Order;
import com.example.orderservice.app.port.in.OrderSearchCommand;
import com.example.orderservice.app.port.out.LoadOrderPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest {

    @Mock
    LoadOrderPort loadOrderPort;

    @InjectMocks
    private OrderQueryService orderQueryService;

    private final List<Order> orderList = new ArrayList<>();

    @BeforeEach
    void init() {
        orderList.add(Order.builder()
                .id("00000000-aaaa-bbbb-00000000")
                .customerId(1L)
                .status(OrderStatus.PAID)
                .streetAddress("경기도 하남시")
                .createdAt(LocalDateTime.of(2025, Month.FEBRUARY, 1, 1, 0))
                .modifiedAt(LocalDateTime.now())
                .build());

        orderList.add(Order.builder()
                .id("11111111-aaaa-bbbb-11111111")
                .customerId(2L)
                .status(OrderStatus.APPROVED)
                .streetAddress("경기도 하남시")
                .createdAt(LocalDateTime.of(2025, Month.FEBRUARY, 2, 1, 0))
                .modifiedAt(LocalDateTime.now())
                .build());

        orderList.add(Order.builder()
                .id("22222222-aaaa-bbbb-22222222")
                .customerId(3L)
                .status(OrderStatus.CANCELLED)
                .streetAddress("경기도 하남시")
                .createdAt(LocalDateTime.of(2025, Month.FEBRUARY, 3, 1, 0))
                .modifiedAt(LocalDateTime.now())
                .build());

        orderList.add(Order.builder()
                .id("33333333-aaaa-bbbb-33333333")
                .customerId(4L)
                .status(OrderStatus.DELETED)
                .streetAddress("경기도 하남시")
                .createdAt(LocalDateTime.of(2025, Month.FEBRUARY, 4, 1, 0))
                .modifiedAt(LocalDateTime.now())
                .build());
    }

    @Test
    void success_find_order_all_with_status_date() {
        var command = OrderSearchCommand.builder()
                        .status(OrderStatus.PAID)
                        .fromDate(LocalDateTime.of(2025, Month.FEBRUARY, 1, 0, 0))
                        .toDate(LocalDateTime.of(2025, Month.FEBRUARY, 2, 0, 0))
                        .build();
        when(loadOrderPort.loadAllOrderInfo()).thenReturn(orderList);

        var result = orderQueryService.findAllOrderInfo(command);
        assertEquals(1, result.size());
    }

    @Test
    void success_find_order_all_with_customer_status_date() {
        final var customerId = 2L;
        var command = OrderSearchCommand.builder()
                .status(OrderStatus.APPROVED)
                .fromDate(LocalDateTime.of(2025, Month.FEBRUARY, 1, 0, 0))
                .toDate(LocalDateTime.of(2025, Month.FEBRUARY, 3, 0, 0))
                .build();
        when(loadOrderPort.loadAllOrderInfoByCustomerId(customerId)).thenReturn(
                orderList.stream().filter(o -> o.getCustomerId() == customerId).collect(Collectors.toList()));

        var result = orderQueryService.findAllOrderInfoByCustomerId(customerId, command);
        assertEquals(1, result.size());
    }

    @Test
    void success_find_order_by_id() {
        final var id = "00000000-aaaa-bbbb-00000000";
        when(loadOrderPort.loadOrderInfo(anyString())).thenReturn(
                orderList.stream().filter(o -> o.getId().equals(id)).findFirst().orElseThrow());

        var result = orderQueryService.findOrderById(id);
        assertEquals(id, result.getId());
    }
}
