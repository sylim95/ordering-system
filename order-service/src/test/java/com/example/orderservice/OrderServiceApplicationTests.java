package com.example.orderservice;

import com.example.orderservice.api.dto.resquest.OrderDeleteRequest;
import com.example.orderservice.api.dto.resquest.OrderSaveRequest;
import com.example.orderservice.app.domain.enums.OrderStatus;
import com.example.orderservice.app.domain.service.OrderCommandService;
import com.example.orderservice.app.domain.service.OrderQueryService;
import com.example.orderservice.app.port.in.OrderDeleteCommand;
import com.example.orderservice.app.port.in.OrderSaveCommand;
import com.example.orderservice.app.port.in.OrderSearchCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderServiceApplicationTests {

    @Autowired
    private OrderCommandService orderCommandService;

    @Autowired
    private OrderQueryService orderQueryService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Test
    void success_order_save_with_product_update() throws InterruptedException {

        // 스레드에서 동시에 addOrder(product 재고 수정) 호출
        CountDownLatch latch = new CountDownLatch(2);

        // 예외 발생 여부를 저장
        final boolean[] exception = {false, false};

        executorService.submit(() -> {
            try {
                var request = OrderSaveRequest.builder()
                        .customerId(7L)
                        .streetAddress("서울특별시 종로구")
                        .items(List.of(
                                new OrderSaveRequest.OrderSaveItemRequest(
                                        4L, BigDecimal.valueOf(1000), 1),
                                new OrderSaveRequest.OrderSaveItemRequest(
                                        5L, BigDecimal.valueOf(2000), 1)
                        )).build();
                orderCommandService.addOrder(OrderSaveCommand.of(request));
            } catch (Exception e) {
                System.out.println("트랜잭션 1 충돌 발생: " + e.getMessage());
                exception[0] = true;
            } finally {
                latch.countDown();
            }
        });

        executorService.submit(() -> {
            try {
                var request = OrderSaveRequest.builder()
                        .customerId(7L)
                        .streetAddress("서울특별시 종로구")
                        .items(List.of(
                                new OrderSaveRequest.OrderSaveItemRequest(
                                        4L, BigDecimal.valueOf(1000), 1),
                                new OrderSaveRequest.OrderSaveItemRequest(
                                        1L, BigDecimal.valueOf(2000), 1)
                        )).build();
                orderCommandService.addOrder(OrderSaveCommand.of(request));
            } catch (Exception e) {
                System.out.println("트랜잭션 2 충돌 발생: " + e.getMessage());
                exception[1] = true;
            } finally {
                latch.countDown();
            }
        });

        latch.await(); // 모든 스레드가 끝날 때까지 대기
        executorService.shutdown();

        assertThat(exception[0] ^ exception[1])
                .as("재시도 로직을 통해 성공")
                .isFalse();

    }

    @Test
    void success_order_delete_with_product_update() throws InterruptedException {

        // 스레드에서 동시에 deleteOrder(product 재고 수정) 호출
        CountDownLatch latch = new CountDownLatch(2);

        // 예외 발생 여부를 저장
        final boolean[] exception = {false, false};

        var orderList = orderQueryService.findAllOrderInfo(
                OrderSearchCommand.builder().status(OrderStatus.PAID).build());

        executorService.submit(() -> {
            try {
                var request = OrderDeleteRequest.builder().status("CANCELLED").build();
                orderCommandService.deleteOrder(OrderDeleteCommand.of(orderList.get(0).getId(), request));
            } catch (Exception e) {
                System.out.println("트랜잭션 1 충돌 발생: " + e.getMessage());
                exception[0] = true;
            } finally {
                latch.countDown();
            }
        });

        executorService.submit(() -> {
            try {
                var request = OrderDeleteRequest.builder().status("CANCELLED").build();
                orderCommandService.deleteOrder(OrderDeleteCommand.of(orderList.get(0).getId(), request));
            } catch (Exception e) {
                System.out.println("트랜잭션 2 충돌 발생: " + e.getMessage());
                exception[1] = true;
            } finally {
                latch.countDown();
            }
        });

        latch.await(); // 모든 스레드가 끝날 때까지 대기
        executorService.shutdown();

        assertThat(exception[0] ^ exception[1])
                .as("재시도 로직을 통해 성공")
                .isFalse();
    }
}
