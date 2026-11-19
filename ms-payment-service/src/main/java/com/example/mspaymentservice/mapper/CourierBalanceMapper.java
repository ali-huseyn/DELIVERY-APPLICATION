package com.example.mspaymentservice.mapper;

import com.example.mspaymentservice.dto.OrderEvent;
import com.example.mspaymentservice.entity.CourierBalanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface CourierBalanceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courierId", source = "courierId")
    @Mapping(target = "balance", constant = "0")
    @Mapping(target = "totalBalance", constant = "0")
    CourierBalanceEntity toEntity(OrderEvent orderEvent);
}
