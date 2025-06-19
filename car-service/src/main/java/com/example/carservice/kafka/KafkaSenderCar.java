package com.example.carservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class KafkaSenderCar {
    //отправляем событие

    @Autowired
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplateBooking;
    @Autowired
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplatePayment;

    public void reservedCarEvent(KafkaEvent kafkaEvent) {
        sendEventBooking("booking-topik", kafkaEvent);
    }

    private void sendEventBooking(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplateBooking.send(topic, kafkaEvent);
    }

    private void sendEventPayment(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplatePayment.send(topic, kafkaEvent);
    }
}
