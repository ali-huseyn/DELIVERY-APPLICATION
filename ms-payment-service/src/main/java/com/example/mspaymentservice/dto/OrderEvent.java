package com.example.mspaymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class OrderEvent {
    private Long orderId;
    private Long courierId;
    private String courierName;
    private BigDecimal deliveryPrice;
    private String customerEmail;
}
