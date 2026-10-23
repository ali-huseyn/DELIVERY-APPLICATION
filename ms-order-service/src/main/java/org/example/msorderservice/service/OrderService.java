package org.example.msorderservice.service;


import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.msorderservice.client.CourierClient;
import org.example.msorderservice.client.response.CourierResponseDto;
import org.example.msorderservice.dto.OrderRequestDto;
import org.example.msorderservice.dto.OrderResponseDto;
import org.example.msorderservice.entity.OrderEntity;
import org.example.msorderservice.entity.OrderHistoryEntity;
import org.example.msorderservice.exception.InvalidOrderStatusException;
import org.example.msorderservice.exception.OrderNotFound;
import org.example.msorderservice.mapper.OrderHistoryMapper;
import org.example.msorderservice.mapper.OrderMapper;
import org.example.msorderservice.repository.OrderHistoryRepository;
import org.example.msorderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.example.msorderservice.enums.OrderStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final CourierClient courierClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderHistoryMapper orderHistoryMapper;


    private BigDecimal calculateDeliveryPrice() {
        return new BigDecimal("5.00");
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {


        var order = new OrderEntity();

        try {
            var courier = getAvailableCourier();
            var earnMany = calculateDeliveryPrice();
            order = orderMapper.toEntity(orderRequestDto, courier, earnMany);


            log.info("Order assigned for courier {}", courier.getId());


        } catch (FeignException ex) {

            if (ex.status() == 409) {
                order = orderMapper.toRejectedEntity(orderRequestDto);
                log.info("Order rejected because courier is not available");
            } else {
                throw ex;
            }


        }


        var saveOrder = orderRepository.save(order);
        var orderHistoryEntity = new OrderHistoryEntity();
        orderHistoryEntity = orderHistoryMapper.toOrderHistoryEntity(saveOrder);
        orderHistoryRepository.save(orderHistoryEntity);
        return orderMapper.toResponseDto(saveOrder);


    }

    public OrderResponseDto getOrderById(Long id) {
        var Order = fetchOrderId(id);
        return orderMapper.toResponseDto(Order);
    }


    public OrderResponseDto pickupOrder(Long orderId) {
        var order = fetchOrderId(orderId);
        if (order.getOrderStatus() != ASSIGNED) {

            throw new InvalidOrderStatusException("Order must be ASSIGNED before pickup");

        }
        order.setOrderStatus(PICKED_UP);
        log.info("Order picked up for order {}", orderId);
        var saveOrder = orderRepository.save(order);

        var orderHistoryEntity = new OrderHistoryEntity();
        orderHistoryEntity = orderHistoryMapper.toOrderHistoryEntity(saveOrder);
        orderHistoryRepository.save(orderHistoryEntity);


        return orderMapper.toResponseDto(saveOrder);
    }

    public OrderResponseDto deliverOrder(Long orderId) {
        var order = fetchOrderId(orderId);
        if (order.getOrderStatus() != PICKED_UP) {
            throw new InvalidOrderStatusException("Order must be PICKED_UP before deliver");
        }

        order.setOrderStatus(DELIVERED);

        var saveOrder = orderRepository.save(order);
        var orderHistoryEntity = new OrderHistoryEntity();
        orderHistoryEntity = orderHistoryMapper.toOrderHistoryEntity(saveOrder);
        orderHistoryRepository.save(orderHistoryEntity);

        return orderMapper.toResponseDto(saveOrder);
    }

    public CourierResponseDto getAvailableCourier() {

        return courierClient.getAvailableCourier();

    }

    public OrderEntity fetchOrderId(Long orderId) {

        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFound("Order not found"));
    }


}
