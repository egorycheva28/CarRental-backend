package com.example.dto.response;

import com.example.dto.Pagination;

import java.util.List;

public record ListPayments(
        List<GetPayment> cars,
        Pagination pagination
) {
}
