package org.example.msorderservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.msorderservice.dto.OrderRequestDto;
import org.example.msorderservice.dto.OrderResponseDto;
import org.example.msorderservice.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        return orderService.createOrder(orderRequestDto);
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    //PATCH /orders/{id}/deliver
    @PatchMapping("/{id}/pickup")
    public OrderResponseDto pickupOrder(@PathVariable Long id) {

        return orderService.pickupOrder(id);

    }

    @PatchMapping("/{id}/deliver")
    public OrderResponseDto deliverOrder(@PathVariable Long id) {
        return orderService.deliverOrder(id);
    }
}
