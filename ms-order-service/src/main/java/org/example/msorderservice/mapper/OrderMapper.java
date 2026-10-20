package org.example.msorderservice.mapper;

import org.example.msorderservice.dto.OrderRequestDto;
import org.example.msorderservice.dto.OrderResponseDto;
import org.example.msorderservice.entity.OrderEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface OrderMapper {

    OrderEntity toEntity(OrderRequestDto orderRequestDto);
    OrderResponseDto toResponseDto(OrderEntity orderEntity);

}
