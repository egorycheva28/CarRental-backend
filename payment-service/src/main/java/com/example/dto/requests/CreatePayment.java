package com.example.dto.requests;

import com.example.model.StatusPayment;

public record CreatePayment(
        String name,
        StatusPayment status,
        Long price
) {
}
