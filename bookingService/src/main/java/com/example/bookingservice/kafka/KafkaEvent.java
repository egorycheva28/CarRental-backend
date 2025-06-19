package com.example.bookingservice.kafka;

import java.util.UUID;

public record KafkaEvent(
        UUID carId,
        UUID bookingId,
        UUID paymentId
) {
}
