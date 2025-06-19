package com.example.bookingservice.kafka;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class KafkaSenderBooking {
    //отправляем событие

    @Autowired
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;

    public void createBooking(KafkaEvent kafkaEvent) {
        sendEvent("booking-topik", kafkaEvent);
    }

    public void completeBooking(KafkaEvent kafkaEvent) {
        sendEvent("booking-topik", kafkaEvent);
    }

    public void cancelBooking(KafkaEvent kafkaEvent) {
        sendEvent("booking-topik", kafkaEvent);
    }

    public void createPayment(KafkaEvent kafkaEvent) {
        sendEvent("booking-topik", kafkaEvent);
    }

    private void sendEvent(String topic, KafkaEvent kafkaEvent) {
        kafkaTemplate.send(topic, kafkaEvent);
    }

}
