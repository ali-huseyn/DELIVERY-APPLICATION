package com.example.mspaymentservice.mapper;

import com.example.mspaymentservice.dto.OrderEvent;
import com.example.mspaymentservice.entity.PaymentEntity;
import com.example.mspaymentservice.enums.PaymentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PaymentMapper {


    PaymentEntity toEntity(OrderEvent orderEvent, PaymentStatus paymentStatus);


}
