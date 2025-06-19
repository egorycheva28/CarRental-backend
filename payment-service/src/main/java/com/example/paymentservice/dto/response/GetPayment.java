package com.example.paymentservice.dto.response;

import com.example.paymentservice.model.StatusPayment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record GetPayment(
        UUID id,
        LocalDateTime createDate,
        LocalDateTime editDate,
        StatusPayment statusPayment,
        UUID userId,
        UUID bookingId
) {
}
