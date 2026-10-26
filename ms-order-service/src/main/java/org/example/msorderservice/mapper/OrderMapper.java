package org.example.msorderservice.mapper;

import org.example.msorderservice.client.response.CourierResponseDto;
import org.example.msorderservice.dto.OrderRequestDto;
import org.example.msorderservice.dto.OrderResponseDto;
import org.example.msorderservice.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courierId", source = "courier.id")
    @Mapping(target = "deliveryPrice", source = "earnMany")
    @Mapping(target = "orderStatus", constant = "ASSIGNED")
    OrderEntity toEntity(OrderRequestDto orderRequestDto, CourierResponseDto courier, BigDecimal earnMany);

    OrderResponseDto toResponseDto(OrderEntity orderEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courierId", ignore = true)
    @Mapping(target = "deliveryPrice", ignore = true)
    @Mapping(target = "orderStatus", constant = "REJECTED")
    OrderEntity toRejectedEntity(OrderRequestDto orderRequestDto);




}
