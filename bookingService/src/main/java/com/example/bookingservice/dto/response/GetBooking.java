package com.example.bookingservice.dto.response;

import java.util.Date;
import java.util.UUID;

public record GetBooking(
        UUID id,
        Date createDate,
        Date editDate,
        UUID userId,
        UUID carId
) {
}
