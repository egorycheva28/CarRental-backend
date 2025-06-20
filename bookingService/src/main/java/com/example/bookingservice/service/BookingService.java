package com.example.bookingservice.service;

import com.example.bookingservice.dto.requests.CreateBooking;
import com.example.bookingservice.dto.response.AvailabilityBookingResponse;
import com.example.bookingservice.dto.response.ListBookings;
import com.example.bookingservice.dto.response.SuccessResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface BookingService {
    AvailabilityBookingResponse checkAvailability(UUID carId);

    UUID createBooking(CreateBooking createBooking);

    SuccessResponse completeBooking(UUID bookingId);

    ListBookings getUserBookingHistory(Long size, Long current);

    ListBookings getBookingHistory(UUID userId, UUID carId, Long size, Long current);
}

