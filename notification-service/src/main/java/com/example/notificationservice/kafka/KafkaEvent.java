package com.example.notificationservice.kafka;

import java.util.UUID;

public record KafkaEvent(
        UUID carId,
        UUID bookingId,
        UUID paymentId,
        UUID userId,
        String email
) {
}