package com.example.bookingservice.dto;

public record Pagination(
        Long size,
        int count,
        Long current
) {
}
