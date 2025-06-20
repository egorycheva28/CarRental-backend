package com.example.bookingservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class KafkaSenderBooking {
    @Autowired
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;

    public void createBooking(KafkaEvent kafkaEvent) {
        sendEvent("booking-topik", kafkaEvent);
    }

    public void completeBooking(KafkaEvent kafkaEvent) {
        sendEvent("booking10-topik", kafkaEvent);
    }

    public void cancelBooking(KafkaEvent kafkaEvent) {
        sendEvent("booking2-topik", kafkaEvent);
    }

    public void createPayment(KafkaEvent kafkaEvent) {
        sendEvent("payment1-topik", kafkaEvent);
    }

    public void cancelPayment(KafkaEvent kafkaEvent) {
        sendEvent("payment3-topik", kafkaEvent);
    }

    public void doPayment(KafkaEvent kafkaEvent) {
        sendEvent("payment5-topik", kafkaEvent);
    }

    private void sendEvent(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplate.send(topic, kafkaEvent);
    }

}
