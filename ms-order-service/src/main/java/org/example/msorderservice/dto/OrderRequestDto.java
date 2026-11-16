package org.example.msorderservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderRequestDto {
    @NotBlank(message = "PickUpAddress cannot be empty")
    private String pickupAddress;
    @NotBlank(message = "Delivery cannot be empty")
    private String deliveryAddress;
    @Email
    private String email;

}
