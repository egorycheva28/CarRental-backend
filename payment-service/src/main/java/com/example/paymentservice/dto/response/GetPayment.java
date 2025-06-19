package com.example.paymentservice.dto.response;

import com.example.paymentservice.model.StatusPayment;

import java.util.Date;
import java.util.UUID;

public record GetPayment(
        UUID id,
        Date createDate,
        Date editDate,
        StatusPayment statusPayment,
        UUID userId,
        UUID bookingId
) {
}
