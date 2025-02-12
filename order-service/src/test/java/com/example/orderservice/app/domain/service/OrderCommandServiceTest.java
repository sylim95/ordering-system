package com.example.orderservice.app.domain.service;


import com.example.orderservice.app.domain.enums.OrderStatus;
import com.example.orderservice.app.domain.model.Order;
import com.example.orderservice.app.domain.model.OrderItem;
import com.example.orderservice.app.port.in.OrderUpdateCommand;
import com.example.orderservice.app.port.out.LoadOrderPort;
import com.example.orderservice.app.port.out.ProductPort;
import com.example.orderservice.app.port.out.SaveOrderPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest {

    @Mock
    private ProductPort productPort;

    @Mock
    private SaveOrderPort saveOrderPort;

    @Mock
    private LoadOrderPort loadOrderPort;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private OrderCommandService orderCommandService;

    @Test
    void fail_order_update_of_status() {
        var orderItem = OrderItem.builder()
                    .id("11111111-aaaa-bbbb-11111111")
                    .orderId("00000000-aaaa-bbbb-00000000")
                    .productId(1L)
                    .price(BigDecimal.valueOf(10000))
                    .quantity(10)
                    .subTotal(BigDecimal.valueOf(100000))
                .build();

        when(loadOrderPort.loadOrderInfo(any())).thenReturn(
                Order.builder()
                        .id("00000000-aaaa-bbbb-00000000")
                        .status(OrderStatus.APPROVED)
                        .customerId(3L)
                        .streetAddress("부산특별시 수영구")
                        .items(List.of(orderItem))
                        .totalPrice(BigDecimal.valueOf(100000))
                        .totalQuantity(10)
                        .build()
        );

        var command = OrderUpdateCommand.builder()
                        .streetAddress("서울특별시 중랑구")
                        .build();

        assertThrows(IllegalArgumentException.class, () -> orderCommandService.updateOrder(command));
    }

}