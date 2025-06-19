package com.example.bookingservice.dto.response;

import com.example.bookingservice.model.StatusBooking;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record GetBooking(
        UUID id,
        LocalDateTime createDate,
        LocalDateTime editDate,
        StatusBooking statusBooking,
        UUID userId,
        UUID carId
) {
}
