package com.example.paymentservice.dto.requests;

import com.example.paymentservice.model.StatusPayment;

public record CreatePayment(
        StatusPayment statusPayment
) {
}
