package com.example.orderservice.app.domain.event.handler;

import com.example.orderservice.app.domain.event.OrderCreatedEvent;
import com.example.orderservice.app.port.out.ProductPort;
import com.example.orderservice.app.port.out.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {

    private final ProductPort productPort;

    /**
     * 주문이 실패했을 경우(AFTER_ROLLBACK) 상품 재고 롤백 처리
     * @param event
     * */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("주문 실패로 인한 상품 재고 롤백 처리: {}", event.orderId());

        try {
            var products = new ArrayList<ProductDto>();
            event.products().forEach(p -> products.add(ProductDto.builder()
                    .id(p.getId())
                    .quantity(-p.getQuantity())
                    .build()));
            productPort.fetchProducts(products);
        } catch (Exception e) {
            log.error("상품 재고 롤백 처리 실패: {}", event.orderId());
        }
    }
}
