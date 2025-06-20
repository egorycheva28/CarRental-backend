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
    @Bean
    public NewTopic bookingtopik1() {
        return TopicBuilder.name("booking1-topik").build();
    }
    @Bean
    public NewTopic bookingtopik10() {
        return TopicBuilder.name("booking10-topik").build();
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
    @Bean
    public NewTopic paymenttopik4() {
        return TopicBuilder.name("payment4-topik").build();
    }
    @Bean
    public NewTopic paymenttopik5() {
        return TopicBuilder.name("payment5-topik").build();
    }
}
