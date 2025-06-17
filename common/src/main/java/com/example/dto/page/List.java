package com.example.dto.page;

public record List<T>(
        List<T> cars,
        Pagination pagination
) {
}
