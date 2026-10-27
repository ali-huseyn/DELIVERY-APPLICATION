package org.example.msorderservice.client;

import org.example.msorderservice.client.response.CourierResponseDto;
import org.example.msorderservice.enums.CourierStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "ms-courier-service",
        url = "http://localhost:8083/couriers"
)
public interface CourierClient {
    @GetMapping("/available")
    CourierResponseDto getAvailableCourier();

    @PatchMapping("/{id}/status")
    CourierResponseDto updateCourierStatus(
            @PathVariable("id") Long id,
            @RequestBody CourierStatus status
    );
}
