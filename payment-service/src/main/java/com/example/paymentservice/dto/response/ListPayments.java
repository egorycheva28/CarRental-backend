package com.example.paymentservice.dto.response;

import com.example.paymentservice.dto.Pagination;

import java.util.List;

public record ListPayments(
        List<GetPayment> payments,
        Pagination pagination
) {
}
