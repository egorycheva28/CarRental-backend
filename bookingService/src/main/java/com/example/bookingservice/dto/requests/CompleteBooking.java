package com.example.bookingservice.dto.requests;

import java.util.UUID;

public record CompleteBooking(
        UUID bookingId,
        UUID carId
) {
}
