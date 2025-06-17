package com.example.dto.page;

public record Pagination(
        Long size,
        int count,
        Long current
) {
}
