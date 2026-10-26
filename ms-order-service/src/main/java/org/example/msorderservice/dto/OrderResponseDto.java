package org.example.msorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msorderservice.enums.OrderStatus;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {
    private Long id;
    private String pickupAddress;
    private String deliveryAddress;
    private Long courierId;
    private String courierName;
    private BigDecimal deliveryPrice;
    private OrderStatus orderStatus;

}
