package com.example.dto.response;

import com.example.model.StatusPayment;

import java.util.UUID;

public record GetPayment(
        UUID id,
        String name,
        StatusPayment status,
        Long price
) {
}
