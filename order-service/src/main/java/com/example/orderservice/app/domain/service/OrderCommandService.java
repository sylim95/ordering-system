package com.example.orderservice.app.domain.service;

import com.example.orderservice.app.domain.enums.OrderStatus;
import com.example.orderservice.app.domain.event.OrderCreatedEvent;
import com.example.orderservice.app.domain.model.Order;
import com.example.orderservice.app.port.in.OrderCommandUseCase;
import com.example.orderservice.app.port.in.OrderDeleteCommand;
import com.example.orderservice.app.port.in.OrderSaveCommand;
import com.example.orderservice.app.port.in.OrderUpdateCommand;
import com.example.orderservice.app.port.out.LoadOrderPort;
import com.example.orderservice.app.port.out.ProductPort;
import com.example.orderservice.app.port.out.SaveOrderPort;
import com.example.orderservice.app.port.out.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCommandService implements OrderCommandUseCase {

    private final ProductPort productPort;
    private final LoadOrderPort loadOrderPort;
    private final SaveOrderPort saveOrderPort;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public Order addOrder(OrderSaveCommand command) {
        Order order = saveOrderPort.save(Order.createOrder(command));

        // 제품 재고 처리
        var productDto = processProducts(order, command.getStatus());

        // 이벤트를 통해 상품 보상 트랜잭션 실행
        eventPublisher.publishEvent(new OrderCreatedEvent(order.getId(), productDto));

        return order;
    }

    private List<ProductDto> processProducts(Order order, OrderStatus status) {
        // 주문 생성 시 재고 차감, 주문 취소 시 재고 복구
        var quantity = Objects.equals(OrderStatus.APPROVED, status) ? 1 : -1;
        var productDto = new ArrayList<ProductDto>();

        order.getItems().forEach(item ->
                productDto.add(ProductDto.builder()
                        .id(item.getProductId())
                        .quantity(quantity * item.getQuantity())
                        .build()));
        productPort.fetchProducts(productDto);

        return productDto;
    }

    @Override
    @Transactional
    public Order updateOrder(OrderUpdateCommand command) {
        Order order = loadOrderPort.loadOrderInfo(command.getId());
        // PAID 상태가 아닌 주문의 경우 변경 불가
        if(order.isInvalidateOrder(OrderStatus.PAID)) {
            throw new IllegalArgumentException("변경할 수 없는 주문 정보입니다. : " + order.getStatus());
        }

        order.updateOrder(command);
        saveOrderPort.save(order);

        return order;
    }

    @Override
    @Transactional
    public Order deleteOrder(OrderDeleteCommand command) {
        Order order = loadOrderPort.loadOrderInfo(command.getId());

        // PAID 또는 CANCELLED 상태가 아닌 주문의 경우 취소 또는 삭제 불가
        if(order.isInvalidateOrder(OrderStatus.PAID, OrderStatus.CANCELLED)) {
            throw new IllegalArgumentException("올바르지 않은 주문 정보입니다.");
        }

        // 요청 상태와 주문 상태가 같을 경우 그대로 반환
        if(Objects.equals(command.getStatus(), order.getStatus())) {
            return order;
        }

        // PAID 상태에서 취소 또는 삭제 실행할 경우 재고 복구
        if(Objects.equals(order.getStatus(), OrderStatus.PAID)) {
            var productDto = processProducts(order, command.getStatus());

            eventPublisher.publishEvent(new OrderCreatedEvent(order.getId(), productDto));
        }

        order.deleteOrder(command);
        saveOrderPort.save(order);

        return order;
    }
}
