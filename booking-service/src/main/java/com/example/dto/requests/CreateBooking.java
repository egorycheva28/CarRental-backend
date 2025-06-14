package com.example.dto.requests;

public record CreateBooking(
        String name,
        StatusPayment status,
        Long price
) {
}
