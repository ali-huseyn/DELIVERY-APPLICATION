package com.example.msnotificationservice.consumer;

import com.example.msnotificationservice.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;



@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final ObjectMapper objectMapper;



    @RabbitListener(queues = "order.queue")
    public void receiveMessage(String message) {
        log.info("ActionLog.receiveMessage.start message: {}", message);

        OrderEvent event = objectMapper.readValue(message, OrderEvent.class);

        log.info("ActionLog.receiveMessage.end event: {}", event.toString());
    }
}