package com.example.orderservice.infra.mapper;

import com.example.orderservice.app.domain.model.Order;
import com.example.orderservice.app.domain.model.OrderItem;
import com.example.orderservice.infra.entity.OrderEntity;
import com.example.orderservice.infra.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    List<Order> toDomainList(List<OrderEntity> list);

    Order toDomain(OrderEntity entity);

    List<OrderItem> toDomainItemList(List<OrderItemEntity> entities);

    @Mapping(target = "orderId", source = "order", qualifiedByName = "mapOrderItem")
    OrderItem toDomainItem(OrderItemEntity domain);

    OrderEntity toEntity(Order domain);

    List<OrderItemEntity> toEntityItemList(List<OrderItem> domains);

    @Mapping(target = "order", source = "orderId", qualifiedByName = "mapOrderEntity")
    OrderItemEntity toEntityItem(OrderItem domain);

    @Named("mapOrderEntity")
    default OrderEntity mapOrderEntity(String orderId) {
        if (orderId == null) {
            return null;
        }
        return OrderEntity.builder().id(orderId).build();
    }

    @Named("mapOrderItem")
    default String mapOrderItem(OrderEntity order) {
        if (order == null) {
            return null;
        }
        return order.getId();
    }
}
