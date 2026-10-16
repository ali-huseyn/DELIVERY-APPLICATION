package org.example.msorderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.msorderservice.dto.CourierResponseDto;
import org.example.msorderservice.dto.OrderRequestDto;
import org.example.msorderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

//    @GetMapping("/test-courier")
//    @ResponseStatus(HttpStatus.OK)
//    private CourierResponseDto testCourier(){
//        return orderService.getAvailableCourier();
//    }

    @PostMapping
    public void createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.creatOrder(orderRequestDto);
    }
}
