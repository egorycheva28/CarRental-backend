package com.example.mapper;

import com.example.dto.Pagination;
import com.example.dto.requests.CreatePayment;
import com.example.dto.response.GetPayment;
import com.example.dto.response.ListPayments;
import com.example.model.Car;
import com.example.model.Payment;

import java.util.List;

public class PaymentMapper {

    public static Payment createCar(CreatePayment createPayment) {
        Payment car = new Payment();
        car.setName(createPayment.name());
        car.setStatus(createPayment.status());
        car.setPrice(createPayment.price());
        return car;
    }

    public static GetPayment getPayment(Payment payment) {
        return new GetPayment(
                payment.getId(),
                payment.getName(),
                payment.getStatus(),
                payment.getPrice()
        );
    }

    public static ListPayments listPayments(List<GetPayment> listPayments, Pagination pagination) {
        return new ListPayments(
                listPayments,
                pagination
        );
    }

}
