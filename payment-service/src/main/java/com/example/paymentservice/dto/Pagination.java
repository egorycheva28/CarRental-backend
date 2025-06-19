package com.example.paymentservice.dto;

public record Pagination(
        Long size,
        int count,
        Long current
) {
}
