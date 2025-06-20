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
        sendEvent("createBookingTopik", kafkaEvent);
    }

    public void completeBooking(KafkaEvent kafkaEvent) {
        sendEvent("completeBookingTopik", kafkaEvent);
    }

    public void cancelBooking1(KafkaEvent kafkaEvent) {
        sendEvent("cancelBookingTopik1", kafkaEvent);
    }

    public void cancelBooking2(KafkaEvent kafkaEvent) {
        sendEvent("cancelBookingTopik2", kafkaEvent);
    }

    public void createPayment(KafkaEvent kafkaEvent) {
        sendEvent("createPaymentTopik", kafkaEvent);
    }

    public void cancelPayment(KafkaEvent kafkaEvent) {
        sendEvent("cancelCarTopik", kafkaEvent);
    }

    public void doPayment(KafkaEvent kafkaEvent) {
        sendEvent("doPayment", kafkaEvent);
    }

    private void sendEvent(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplate.send(topic, kafkaEvent);
    }

}
