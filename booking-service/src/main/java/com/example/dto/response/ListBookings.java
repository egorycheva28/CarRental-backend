package com.example.dto.response;

import com.example.dto.Pagination;

import java.util.List;

public record ListBookings(
        List<GetBooking> cars,
        Pagination pagination
) {
}
