package com.example.dto.requests;

import com.example.model.Status;

public record CreateCar(
        String name,
        Status status,
        Long price
) {
}
