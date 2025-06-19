package com.example.paymentservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicPayment {
    @Bean
    public NewTopic bookingtopik() {
        return TopicBuilder.name("booking-topik").build();
    }
    @Bean
    public NewTopic bookingtopik1() {
        return TopicBuilder.name("booking1-topik").build();
    }
    @Bean
    public NewTopic bookingtopik2() {
        return TopicBuilder.name("booking2-topik").build();
    }
    @Bean
    public NewTopic paymenttopik1() {
        return TopicBuilder.name("payment1-topik").build();
    }
    @Bean
    public NewTopic paymenttopik2() {
        return TopicBuilder.name("payment2-topik").build();
    }
    @Bean
    public NewTopic paymenttopik3() {
        return TopicBuilder.name("payment3-topik").build();
    }
}

