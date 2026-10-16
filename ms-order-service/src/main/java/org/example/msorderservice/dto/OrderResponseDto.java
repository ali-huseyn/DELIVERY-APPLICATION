package org.example.msorderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResponseDto {
    private Long id;
    private String pickupAddress;
    private String deliveryAddress;
    private Long courierId;
    private String deliveryPrice;
    private String status;

}
