package com.example.dto.response;

import com.example.model.Status;

import java.util.UUID;

public record GetCar(
        UUID id,
        String name,
        Status status,
        Long price
) {
}
