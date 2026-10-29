package com.example.msnotificationservice.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEvent {

    private Long orderId;
    private Long courierId;
    private String courierName;
    private BigDecimal deliveryPrice;
}