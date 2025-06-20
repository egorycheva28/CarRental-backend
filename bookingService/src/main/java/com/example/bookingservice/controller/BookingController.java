package com.example.bookingservice.controller;

import com.example.bookingservice.dto.requests.CreateBooking;
import com.example.bookingservice.dto.response.AvailabilityBookingResponse;
//import com.example.service.BookingService;
import com.example.bookingservice.dto.response.ListBookings;
import com.example.bookingservice.dto.response.SuccessResponse;
import com.example.bookingservice.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
@Tag(name = "Booking")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/check/{carId}")
    @Operation(summary = "Проверка возможности аренды автомобиля")
    public AvailabilityBookingResponse checkAvailability(@PathVariable(name = "carId") UUID carId) {
        System.out.println(carId);
        return bookingService.checkAvailability(carId);
    }

    @PostMapping("/create")
    @Operation(summary = "Бронирование автомобиля")
    public UUID createBooking(@RequestBody CreateBooking createBooking) {
        return bookingService.createBooking(createBooking);
    }

    @PostMapping("/complete/{id}")
    @Operation(summary = "Завершение аренды")
    public SuccessResponse completeBooking(@PathVariable(name = "id") UUID bookingId) {
        return bookingService.completeBooking(bookingId);
    }

    @GetMapping("/history/booking/user")
    @Operation(summary = "Получение своей истории бронирования")
    public ListBookings getUserBookingHistory(Long size, Long current) {
        return bookingService.getUserBookingHistory(size, current);
    }

    @GetMapping("/history/booking")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Получение истории бронирования конкретного автомобиля или пользователя (для админа)")
    public ListBookings getBookingHistory(@RequestParam(value = "userId", required = false) UUID userId, @RequestParam(value = "carId", required = false) UUID carId, Long size, Long current) {
        return bookingService.getBookingHistory(userId, carId, size, current);
    }
}
