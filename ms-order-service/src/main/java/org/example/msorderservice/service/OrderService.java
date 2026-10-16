package org.example.msorderservice.service;


import lombok.RequiredArgsConstructor;
import org.example.msorderservice.client.CourierClient;
import org.example.msorderservice.dto.CourierResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CourierClient courierClient;

    public CourierResponseDto orderCreate(){

        return courierClient.getAvailableCourier();

    }
}
