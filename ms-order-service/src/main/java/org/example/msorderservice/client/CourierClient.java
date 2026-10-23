package org.example.msorderservice.client;

import org.example.msorderservice.client.response.CourierResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "ms-courier-service",
        url = "http://localhost:8083/couriers"
)
public interface CourierClient {
    @GetMapping("/available")
    CourierResponseDto getAvailableCourier();
}
