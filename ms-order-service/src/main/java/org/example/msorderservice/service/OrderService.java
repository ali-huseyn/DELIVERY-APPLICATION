package org.example.msorderservice.service;


import lombok.RequiredArgsConstructor;
import org.example.msorderservice.client.CourierClient;
import org.example.msorderservice.dto.CourierResponseDto;
import org.example.msorderservice.dto.OrderRequestDto;
import org.example.msorderservice.dto.OrderResponseDto;
import org.example.msorderservice.enums.OrderStatus;
import org.example.msorderservice.mapper.OrderMapper;
import org.example.msorderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.example.msorderservice.enums.OrderStatus.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CourierClient courierClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public CourierResponseDto getAvailableCourier(){

        return courierClient.getAvailableCourier();

    }
    private BigDecimal calculateDeliveryPrice() {
        return new BigDecimal("5.00");
    }

    public OrderResponseDto createOrder(OrderRequestDto  orderRequestDto){

        var courier=getAvailableCourier();
        var order=orderMapper.toEntity(orderRequestDto);
        if(courier!=null){
            order.setDeliveryPrice(calculateDeliveryPrice());
            order.setCourierId(courier.getId());
            order.setOrderStatus(ASSIGNED);
        }else{
            order.setOrderStatus(REJECTED);
        }
           var saveOrder= orderRepository.save(order);
           return orderMapper.toResponseDto(saveOrder);


    }
    public OrderResponseDto getOrderById(Long orderId){
        var order=orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new RuntimeException("Order not found");
        }
        return orderMapper.toResponseDto(order.get());
    }
    public OrderResponseDto pickupOrder(Long orderId){
        var order=orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new RuntimeException("Order not found");
        }
        if (order.get().getOrderStatus() != ASSIGNED) {


            throw new RuntimeException(
                    "Order must be ASSIGNED before pickup");

        }
        order.get().setOrderStatus(PICKED_UP);
        var saveOrder= orderRepository.save(order.get());


        return orderMapper.toResponseDto(saveOrder);
    }
    public OrderResponseDto deliverOrder(Long orderId){
        var order=orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new RuntimeException("Order not found");
        }
        if (order.get().getOrderStatus() != PICKED_UP) {
            throw new RuntimeException("Order must be PICKED_UP before deliver");
        }
        order.get().setOrderStatus(DELIVERED);
        var saveOrder= orderRepository.save(order.get());
        return orderMapper.toResponseDto(saveOrder);
    }


}
