package com.example.dto.requests;

import com.example.model.Status;

public record EditCar(
        String name,
        Status status,
        Long price
) {
}
