package com.example.service;

import com.example.dto.requests.CreatePayment;
import com.example.dto.requests.EditPayment;
import com.example.dto.response.GetPayment;
import com.example.dto.response.ListPayments;
import com.example.dto.response.SuccessResponse;
import com.example.model.StatusPayment;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.UUID;

public interface PaymentService {
    ListPayments getPayments(Authentication authentication, StatusPayment statusCar, Long size, Long current);

    GetPayment getPaymentById(UUID carId, Authentication authentication);

    //UUID createCar(CreatePayment createCar);

    //SuccessResponse editCar(UUID carId, EditPayment editCar, Authentication authentication);

    SuccessResponse statusPaid(UUID carId, StatusPayment status);
}
