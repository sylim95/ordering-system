package com.example.orderservice.app.port.in;

import com.example.orderservice.api.dto.resquest.OrderSearchRequest;
import com.example.orderservice.app.domain.enums.OrderStatus;
import com.example.orderservice.utils.DateUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderSearchCommand {
    private OrderStatus status;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;

    public static OrderSearchCommand of(OrderSearchRequest request) {
        return OrderSearchCommand.builder()
                .status(request.getStatus())
                .fromDate(DateUtil.atStartDateTime(request.getFromDate()))
                .toDate(DateUtil.atEndDateTime(request.getToDate()))
                .build();
    }
}
