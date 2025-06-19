package com.example.paymentservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class KafkaSenderPayment {

    @Autowired
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplateBooking;

    public void doPayment(KafkaEvent kafkaEvent) {
        sendEventBooking("payment4-topik", kafkaEvent);
    }

    public void cancelPayment(KafkaEvent kafkaEvent) {
        sendEventBooking("payment2-topik", kafkaEvent);
    }

    private void sendEventBooking(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplateBooking.send(topic, kafkaEvent);
    }
}
