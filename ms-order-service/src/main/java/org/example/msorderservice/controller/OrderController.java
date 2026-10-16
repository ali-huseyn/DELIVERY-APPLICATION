package org.example.msorderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.msorderservice.dto.CourierResponseDto;
import org.example.msorderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @GetMapping("/test-courier")
    @ResponseStatus(HttpStatus.OK)
    private CourierResponseDto testCourier(){
        return orderService.orderCreate();
    }
}
