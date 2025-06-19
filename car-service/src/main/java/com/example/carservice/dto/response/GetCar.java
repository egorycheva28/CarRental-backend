package com.example.carservice.dto.response;

import com.example.carservice.model.Status;

import java.util.UUID;

public record GetCar(
        UUID id,
        String name,
        Status status,
        Long price
) {
}
