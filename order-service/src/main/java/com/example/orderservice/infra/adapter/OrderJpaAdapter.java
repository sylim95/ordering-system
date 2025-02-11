package com.example.orderservice.infra.adapter;

import com.example.common.error.exception.BusinessException;
import com.example.orderservice.app.domain.model.Order;
import com.example.orderservice.app.port.out.LoadOrderPort;
import com.example.orderservice.app.port.out.SaveOrderPort;
import com.example.orderservice.infra.mapper.OrderMapper;
import com.example.orderservice.infra.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderJpaAdapter implements LoadOrderPort, SaveOrderPort {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> loadAllOrderInfo() {
        return OrderMapper.INSTANCE.toDomainList(orderRepository.findAllWithItems());
    }

    @Override
    public Order loadOrderInfo(String id) {
        return OrderMapper.INSTANCE.toDomain(Optional.ofNullable(orderRepository.findByIdWithItems(id))
                .orElseThrow(() -> new BusinessException("올바르지 않은 주문 정보입니다.")));
    }

    @Override
    public List<Order> loadAllOrderInfoByCustomerId(Long customerId) {
        return OrderMapper.INSTANCE.toDomainList(orderRepository.findAllWithItemsByCustomerId(customerId));
    }

    @Override
    public Order save(Order command) {
        return OrderMapper.INSTANCE.toDomain(
                orderRepository.save(OrderMapper.INSTANCE.toEntity(command)));
    }
}
