package com.example.paymentservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicPayment {
    //куда отправляем
    @Bean
    public NewTopic paymenttopik() {
        return TopicBuilder.name("payment-topik").build();
    }
}
