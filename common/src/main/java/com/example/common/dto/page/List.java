package com.example.common.dto.page;

public record List<T>(
        List<T> cars,
        Pagination pagination
) {
}
