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

    @GetMapping("/check/{id}")
    @Operation(summary = "Проверка возможности аренды автомобиля")
    public AvailabilityBookingResponse checkAvailability(@PathVariable(name = "id") UUID carId) {
        return bookingService.checkAvailability(carId);
    }

    @PostMapping("/create")
    @Operation(summary = "Бронирование автомобиля")
    public UUID createBooking(@RequestBody CreateBooking createBooking) {
        return bookingService.createBooking(createBooking);
    }

    //проведение платежа, связать с payment-service


    // Завершение аренды
    /*@Transactional
    public void completeRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RuntimeException("Аренда не найдена"));
        rental.setEndTime(LocalDateTime.now());
        rental.setStatus("Завершено");
        rentalRepository.save(rental);

        // Обновить статус автомобиля
        Car car = rental.getCar();
        car.setStatus("свободен");
        carRepository.save(car);

        // Отправка события Kafka
        kafkaTemplate.send("rental-events", "Аренда завершена ID: " + rentalId);
    }*/

    //Завершение аренды - добавление данных об окончании аренды в запись журнала аренды, перевод статуса автомобиля в "свободен"
    @PostMapping("/complete/{id}")
    public SuccessResponse completeBooking(@PathVariable(name = "id") UUID bookingId) {
        return bookingService.completeBooking(bookingId);
    }


    //получение истории бронирования
    //Для конкретного пользователя (свою историю может просматривать каждый)
    @GetMapping("/history/booking/user")
    @Operation(summary = "Получение своей истории бронирования")
    public ListBookings getUserBookingHistory(Long size, Long current) {
        return bookingService.getUserBookingHistory(size, current);
    }

    //получение истории бронирования
    //Для конкретного автомобиля (только админ)
    //Для конкретного пользователя (чужую - только админ)
    @GetMapping("/history/booking")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Получение истории бронирования конкретного автомобиля или пользователя", description = "для админа")
    public ListBookings getBookingHistory(@RequestParam(value = "userId", required = false) UUID userId, @RequestParam(value = "carId", required = false) UUID carId, Long size, Long current) {
        return bookingService.getBookingHistory(userId, carId, size, current);
    }

}
