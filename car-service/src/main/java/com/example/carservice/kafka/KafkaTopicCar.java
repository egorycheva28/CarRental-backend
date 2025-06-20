package com.example.carservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicCar {
    @Bean
    public NewTopic createBookingTopik() {
        return TopicBuilder.name("createBookingTopik").build();
    }

    @Bean
    public NewTopic reservedBookingTopik() {
        return TopicBuilder.name("reservedBookingTopik").build();
    }

    @Bean
    public NewTopic completeBookingTopik() {
        return TopicBuilder.name("completeBookingTopik").build();
    }

    @Bean
    public NewTopic cancelBookingTopik1() {
        return TopicBuilder.name("cancelBookingTopik1").build();
    }

    @Bean
    public NewTopic cancelBookingTopik2() {
        return TopicBuilder.name("cancelBookingTopik2").build();
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
    public NewTopic cancelCarTopik() {
        return TopicBuilder.name("cancelCarTopik").build();
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
