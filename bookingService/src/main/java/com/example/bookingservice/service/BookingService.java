package com.example.bookingservice.service;

import com.example.bookingservice.dto.requests.CreateBooking;
import com.example.bookingservice.dto.response.AvailabilityBookingResponse;
import com.example.bookingservice.dto.response.ListBookings;
import com.example.bookingservice.dto.response.SuccessResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface BookingService {
    AvailabilityBookingResponse checkAvailability(UUID carId);

    //SuccessResponse changeStatusBooking(UUID carId, AvailabilityBookingResponse availabilityBookingResponse);

    UUID createBooking(CreateBooking createBooking);

    SuccessResponse completeBooking(UUID bookingId);
    //ListBookings getPayments(Authentication authentication, StatusPayment statusCar, Long size, Long current);

    //GetBooking getPaymentById(UUID carId, Authentication authentication);

    //UUID createCar(CreatePayment createCar);

    //SuccessResponse editCar(UUID carId, EditPayment editCar, Authentication authentication);

    //   SuccessResponse statusPaid(UUID carId, StatusPayment status);
    //String cancelBooking();

    ListBookings getUserBookingHistory(Long size, Long current);

    ListBookings getBookingHistory(UUID userId, UUID carId, Long size, Long current);
}

