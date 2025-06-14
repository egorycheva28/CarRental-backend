package com.example.dto.response;

import java.util.UUID;

public record GetBooking(
        UUID id,
        String name,
        StatusPayment status,
        Long price
) {
}
