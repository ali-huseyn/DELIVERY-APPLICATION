package com.example.mspaymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentRabbitMQConfig {

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder
                .durable("payment.queue")
                .build();
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange");
    }

    @Bean
    public Binding orderCreatedBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(orderExchange())
                .with("order.created");
    }

    @Bean
    public Binding orderDeliveredBinding() {
        return BindingBuilder
                .bind(paymentQueue())
                .to(orderExchange())
                .with("order.delivered");
    }
}

