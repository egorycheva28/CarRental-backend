package com.example.paymentservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicPayment {
    @Bean
    public NewTopic createBookingTopik() {
        return TopicBuilder.name("createBookingTopik").build();
    }
    @Bean
    public NewTopic reservedBookingTopik() {
        return TopicBuilder.name("reservedBookingTopik").build();
    }
    @Bean
    public NewTopic cancelBookingTopik() {
        return TopicBuilder.name("cancelBookingTopik").build();
    }
    @Bean
    public NewTopic createPaymentTopik() {
        return TopicBuilder.name("createPaymentTopik").build();
    }
    @Bean
    public NewTopic cancelPaymentTopik() {
        return TopicBuilder.name("cancelPaymentTopik").build();
    }
    @Bean
    public NewTopic cancelPayment() {
        return TopicBuilder.name("cancelPayment").build();
    }
    @Bean
    public NewTopic doPaymentTopik() {
        return TopicBuilder.name("doPaymentTopik").build();
    }
    @Bean
    public NewTopic doPayment() {
        return TopicBuilder.name("doPayment").build();
    }
}

