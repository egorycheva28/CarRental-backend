package com.example.paymentservice.service;

import com.example.paymentservice.dto.response.GetPayment;
import com.example.paymentservice.dto.response.ListPayments;
import com.example.paymentservice.dto.response.SuccessResponse;
import com.example.paymentservice.model.StatusPayment;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface PaymentService {
    ListPayments getAllPayments(StatusPayment statusCar, Long size, Long current);

    ListPayments getPayments(StatusPayment statusCar, Long size, Long current);

    GetPayment getPaymentById(UUID carId);

    SuccessResponse doPayment(UUID paymentId);

    SuccessResponse cancelPayment(UUID paymentId);
}
