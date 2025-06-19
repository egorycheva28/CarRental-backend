package com.example.carservice.kafka;

import java.util.UUID;

public record KafkaEvent(
        UUID carId,
        UUID bookingId,
        UUID paymentId,
        UUID userId
) {
}
