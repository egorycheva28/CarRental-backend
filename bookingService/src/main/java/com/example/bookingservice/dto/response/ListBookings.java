package com.example.bookingservice.dto.response;

import com.example.bookingservice.dto.Pagination;

import java.util.List;

public record ListBookings(
        List<GetBooking> bookings,
        Pagination pagination
) {
}
