package com.example.dto;

public record Pagination(
        Long size,
        int count,
        Long current
) {
}
