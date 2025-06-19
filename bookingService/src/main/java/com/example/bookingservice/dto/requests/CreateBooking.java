package com.example.bookingservice.dto.requests;


import java.util.UUID;

public record CreateBooking(
        UUID carId
) {
}
