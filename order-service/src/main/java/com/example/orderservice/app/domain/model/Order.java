package com.example.orderservice.app.domain.model;


import com.example.orderservice.app.domain.enums.OrderStatus;
import com.example.orderservice.app.port.in.OrderDeleteCommand;
import com.example.orderservice.app.port.in.OrderSaveCommand;
import com.example.orderservice.app.port.in.OrderUpdateCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    /** 주문 아이디 */
    private String id;

    /** 고객 아이디 */
    private Long customerId;

    /** 총 주문 금액 */
    private BigDecimal totalPrice;

    /** 총 주문 수량 */
    private Integer totalQuantity;

    /** 주문 배송지 */
    private String streetAddress;

    /** 주문 상태 */
    private OrderStatus status;

    /** 생성 일자 */
    private LocalDateTime createdAt;

    /** 수정 일자 */
    private LocalDateTime modifiedAt;
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();

    public boolean isInvalidateOrder(OrderStatus... status) {
        return CollectionUtils.isEmpty(this.items) ||
                !Arrays.asList(status).contains(this.status);
    }

    public static Order createOrder(OrderSaveCommand command) {
        final var uuid = UUID.randomUUID().toString();
        var orderItems = convertOrderItem(command, uuid);
        // 총 주문 수량: 각 주문 상품 수량의 합
        int totalQuantity = orderItems.stream().mapToInt(OrderItem::getQuantity).sum();

        // 총 주문 금액: 각 주문 금액(수량 * 상품 가격)의 합
        BigDecimal totalPrice = orderItems.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return Order.builder()
                .id(uuid)
                .customerId(command.getCustomerId())
                .streetAddress(command.getStreetAddress())
                .items(orderItems)
                .totalQuantity(totalQuantity)
                .totalPrice(totalPrice)
                .status(OrderStatus.PAID)
                .build();
    }

    private static List<OrderItem> convertOrderItem(OrderSaveCommand command, String uuid) {
        return command.getItems().stream().map(o -> OrderItem.of(o, uuid)).toList();
    }

    /**
     * 주문 정보 수정
     * - 배송지, 상태 수정 가능
     * @param command
     * */
    public void updateOrder(OrderUpdateCommand command) {
        if(command.getStreetAddress() != null) {
            this.streetAddress = command.getStreetAddress();
        }

        if(command.getStatus() != null) {
            this.status = command.getStatus();
        }
    }

    /**
     * 주문 정보 삭제
     * - 상태 변경(soft delete)
     * @param command
     * */
    public void deleteOrder(OrderDeleteCommand command) {
        this.status = command.getStatus();
    }

}
