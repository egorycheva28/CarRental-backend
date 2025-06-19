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
    @Autowired
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplatePayment;

    public void doPayment(KafkaEvent kafkaEvent) {
        sendEventPayment("payment-topik", kafkaEvent);
    }


    private void sendEventBooking(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplateBooking.send(topic, kafkaEvent);
    }

    private void sendEventPayment(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplatePayment.send(topic, kafkaEvent);
    }
}
