package com.example.paymentservice.dto.requests;

import com.example.paymentservice.model.StatusPayment;

public record EditPayment(
        String name,
        StatusPayment status,
        Long price
) {
}
