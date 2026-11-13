package com.example.mspaymentservice.consumer;

import com.example.mspaymentservice.dto.OrderEvent;
import com.example.mspaymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "payment.queue")
    public void receiveMessage(
            String message,
            @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey) {

        log.info("Received payment event: {}", routingKey);

        OrderEvent event = objectMapper.readValue(message, OrderEvent.class);

        switch (routingKey) {
            case "order.created" ->
                    paymentService.createPendingPayment(event);

            case "order.delivered" ->
                    paymentService.completePayment(event);

            default ->
                    log.warn("Unknown routing key: {}", routingKey);
        }
    }

}
