package com.example.service;

import com.example.dto.requests.CreateBooking;
import com.example.dto.response.AvailabilityBookingResponse;
import com.example.dto.response.GetBooking;
import com.example.dto.response.ListBookings;
import com.example.dto.response.SuccessResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

public interface BookingService {
    AvailabilityBookingResponse checkAvailability(UUID carId);

    SuccessResponse changeStatusBooking(UUID carId, AvailabilityBookingResponse availabilityBookingResponse);

    UUID createBooking(CreateBooking createBooking);

    SuccessResponse completeBooking(UUID bookingId);
    //ListBookings getPayments(Authentication authentication, StatusPayment statusCar, Long size, Long current);

    //GetBooking getPaymentById(UUID carId, Authentication authentication);

    //UUID createCar(CreatePayment createCar);

    //SuccessResponse editCar(UUID carId, EditPayment editCar, Authentication authentication);

    //   SuccessResponse statusPaid(UUID carId, StatusPayment status);
}
