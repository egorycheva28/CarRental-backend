package com.example.carservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class KafkaSenderCar {
    @Autowired
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;

    public void reservedCarEvent(KafkaEvent kafkaEvent) {
        sendEventBooking("booking1-topik", kafkaEvent);
    }

    private void sendEventBooking(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplate.send(topic, kafkaEvent);
    }

    private void sendEventPayment(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplate.send(topic, kafkaEvent);
    }
}
