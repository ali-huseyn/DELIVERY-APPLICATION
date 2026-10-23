package org.example.msorderservice.mapper;

import org.example.msorderservice.entity.OrderEntity;
import org.example.msorderservice.entity.OrderHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface OrderHistoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", source = "id")
    @Mapping(target = "status", source = "orderStatus")
    @Mapping(target = "createdAt", ignore = true)
    OrderHistoryEntity toOrderHistoryEntity(OrderEntity orderEntity);
}
