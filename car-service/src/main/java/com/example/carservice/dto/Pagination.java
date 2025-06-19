package com.example.carservice.dto;

public record Pagination(
        Long size,
        int count,
        Long current
) {
}
