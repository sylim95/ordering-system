package com.example.orderservice.app.domain.service;

import com.example.orderservice.app.domain.model.Order;
import com.example.orderservice.app.port.in.OrderQueryUseCase;
import com.example.orderservice.app.port.in.OrderSearchCommand;
import com.example.orderservice.app.port.out.LoadOrderPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderQueryService implements OrderQueryUseCase {

    private final LoadOrderPort loadOrderPort;

    @Override
    public List<Order> findAllOrderInfo(OrderSearchCommand command) {
        var orderList = loadOrderPort.loadAllOrderInfo();
        filterOrderList(orderList, command);

        return orderList;
    }

    @Override
    public List<Order> findAllOrderInfoByCustomerId(Long customerId, OrderSearchCommand command) {
        var orderList = loadOrderPort.loadAllOrderInfoByCustomerId(customerId);
        filterOrderList(orderList, command);

        return orderList;
    }

    @Override
    public Order findOrderById(String id) {
        return loadOrderPort.loadOrderInfo(id);
    }

    /**
     * 사용자 검색에 따른 필터
     * @param order
     * @param search: 검색 값
     * */
    private void filterOrderList(List<Order> order, OrderSearchCommand search) {
        if(search.getStatus() != null) {
            order.removeIf(o -> !Objects.equals(search.getStatus(), o.getStatus()));
        }

        // 생성일(createdAt)이 검색 시작일(fromDate) 이전이거나
        // 검색 종료일(toDate) 이후인 주문은 제거
        if (search.getFromDate() != null && search.getToDate() != null) {
            order.removeIf(o -> o.getCreatedAt().isBefore(search.getFromDate())
                    || o.getCreatedAt().isAfter(search.getToDate()));
        }
    }
}
