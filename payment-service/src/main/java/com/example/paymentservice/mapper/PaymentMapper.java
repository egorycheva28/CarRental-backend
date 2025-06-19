package com.example.paymentservice.mapper;

import com.example.paymentservice.dto.Pagination;
import com.example.paymentservice.dto.requests.CreatePayment;
import com.example.paymentservice.dto.response.GetPayment;
import com.example.paymentservice.dto.response.ListPayments;
import com.example.paymentservice.model.Payment;

import java.util.List;

public class PaymentMapper {

    public static GetPayment getPayment(Payment payment) {
        return new GetPayment(
                payment.getId(),
                payment.getCreateDate(),
                payment.getEditDate(),
                payment.getStatusPayment(),
                payment.getUserId(),
                payment.getBookingId()
        );
    }

    public static ListPayments listPayments(List<GetPayment> listPayments, Pagination pagination) {
        return new ListPayments(
                listPayments,
                pagination
        );
    }
}
