package org.example.msorderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable("order.queue")
                .withArgument("x-dead-letter-exchange", "order.dlx")
                .withArgument("x-dead-letter-routing-key", "order.failed")
                .build();
    }

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange("order.exchange");
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with("order.created");
    }


    @Bean
    public Binding orderDeliveredBinding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with("order.delivered");
    }

    @Bean
    public Queue deadOrderQueue() {
        return QueueBuilder
                .durable("order.dlq")
                .build();
    }

    @Bean
    public DirectExchange deadOrderExchange() {
        return new DirectExchange("order.dlx");
    }

    @Bean
    public Binding deadOrderBinding() {
        return BindingBuilder
                .bind(deadOrderQueue())
                .to(deadOrderExchange())
                .with("order.failed");
    }

    @Bean
    public JacksonJsonMessageConverter jacksonJsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
