package com.example.msnotificationservice.consumer;

import com.example.msnotificationservice.dto.OrderEvent;
import com.example.msnotificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;



    @RabbitListener(queues = "order.queue")
    public void receiveMessage(
            String message,
            @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey)  {
        log.info("Received routing key: {}", routingKey);
        log.info("ActionLog.receiveMessage.start message: {}", message);

        OrderEvent event = objectMapper.readValue(message, OrderEvent.class);
//        emailService.sendEmail(
//                event.getCustomerEmail(),
//                "Order notification",
//                "Your order#" + event + " has been updated."
//        );

        log.info("ActionLog.receiveMessage.end event: {}", event.toString());
    }
}