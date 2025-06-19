package com.example.carservice.dto.requests;

import com.example.carservice.model.Status;

public record CreateCar(
        String name,
        Status status,
        Long price
) {
}
