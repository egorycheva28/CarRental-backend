package com.example.bookingservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicBooking {
    //куда отправляем
    @Bean
    public NewTopic bookingtopik() {
        return TopicBuilder.name("booking-topik").build();
    }
}
