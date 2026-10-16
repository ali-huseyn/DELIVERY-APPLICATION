package org.example.msorderservice.service;


import lombok.RequiredArgsConstructor;
import org.example.msorderservice.client.CourierClient;
import org.example.msorderservice.dto.CourierResponseDto;
import org.example.msorderservice.dto.OrderRequestDto;
import org.example.msorderservice.enums.OrderStatus;
import org.example.msorderservice.mapper.OrderMapper;
import org.example.msorderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import static org.example.msorderservice.enums.OrderStatus.ASSIGNED;
import static org.example.msorderservice.enums.OrderStatus.REJECTED;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CourierClient courierClient;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public CourierResponseDto getAvailableCourier(){

        return courierClient.getAvailableCourier();

    }

    public void creatOrder(OrderRequestDto  orderRequestDto){

        var courier=getAvailableCourier();
        var order=orderMapper.toEntity(orderRequestDto);
        if(courier!=null){

            order.setCourierId(courier.getId());
            order.setOrderStatus(ASSIGNED);
        }else{
            order.setOrderStatus(REJECTED);
        }
        orderRepository.save(order);


    }
}
