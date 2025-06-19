package com.example.common.dto.page;

public record Pagination(
        Long size,
        int count,
        Long current
) {
}
