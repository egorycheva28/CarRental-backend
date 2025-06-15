package com.example.dto.requests;

import com.example.model.StatusPayment;

public record EditPayment(
        String name,
        StatusPayment status,
        Long price
) {
}
