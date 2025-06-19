package com.example.paymentservice.kafka;

import java.util.UUID;

public record KafkaEvent(
        UUID carId,
        UUID bookingId,
        UUID paymentId
) {
}