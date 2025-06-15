package com.example.controller;

import com.example.dto.requests.CreateBooking;
import com.example.dto.response.AvailabilityBookingResponse;
import com.example.dto.response.GetBooking;
import com.example.dto.response.ListBookings;
import com.example.dto.response.SuccessResponse;
import com.example.model.Booking;
//import com.example.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*@RestController
@RequestMapping("booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    //проверка возможности аренды
    @GetMapping("/check/{id}")
    public AvailabilityBookingResponse checkAvailability(@PathVariable(name = "id") UUID carId) {
        return bookingService.checkAvailability(carId);
    }

    //перевод автомобиля в статус "забронировано"
    @PostMapping("/booked/{id}")
    public SuccessResponse changeStatusBooking(@PathVariable(name = "id") UUID carId, @RequestBody AvailabilityBookingResponse availabilityBookingResponse) {
        return bookingService.changeStatusBooking(carId, availabilityBookingResponse);
    }

    //проведение платежа, связать с payment-service

    //создание записи об аренде
    @PostMapping("/create")
    public UUID createBooking(@RequestBody CreateBooking createBooking) {
        return bookingService.createBooking(createBooking);
    }*/

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
    /*@PostMapping("/complete/{id}")
    public SuccessResponse completeBooking(@PathVariable(name = "id") UUID bookingId) {
        return bookingService.completeBooking(bookingId);
    }*/
    //Сброс статуса "Забронировано", если в течение определённого времени платёж не был произведён, перевод платежа в статус "Отменён"

    //получение истории бронирования
    //Для конкретного пользователя (свою историю может просматривать каждый, чужую - только админ)
    /*@GetMapping("/history/user/{userId}")
    public ListBookings getUserHistory(@PathVariable(name = "id") UUID userId, Long size, Long current) {
        //сравнивать айди пользователей и на наличие, посмотреть для бали

        return bookingService.getCarHistory(userId, size, current);
    }

    //получение истории бронирования
    //Для конкретного автомобиля (только админ)
    @GetMapping("/history/car/{carId}")
    public ListBookings getCarHistory(@PathVariable(name = "id") UUID carId, Long size, Long current) {
        //из токена понять роль (только админ может)
        return bookingService.getCarHistory(carId, size, current);
    }*/

//}
