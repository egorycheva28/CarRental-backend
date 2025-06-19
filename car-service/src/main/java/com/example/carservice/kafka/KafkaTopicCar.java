package com.example.carservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicCar {
    //куда отправляем
    @Bean
    public NewTopic bookingtopik() {
        return TopicBuilder.name("booking-topik").build();
    }
}
