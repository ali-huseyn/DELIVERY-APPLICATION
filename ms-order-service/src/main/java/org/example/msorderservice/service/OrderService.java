package org.example.msorderservice.service;


import lombok.RequiredArgsConstructor;
import org.example.msorderservice.client.CourierClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CourierClient courierClient;

    public void orderCreate(){

        var freeCourier=courierClient.getAvailableCourier();

        System.out.println(freeCourier);

    }
}
