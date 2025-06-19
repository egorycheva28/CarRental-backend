package com.example.bookingservice.kafka;

import com.example.bookingservice.exception.BookingNotFoundException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.StatusBooking;
import com.example.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaListenerBooking {

    private final KafkaSenderBooking kafkaSenderBooking;
    private final BookingRepository bookingRepository;

    @KafkaListener(topics = "booking-topik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryCar")

    public UUID reservedBooking(KafkaEvent kafkaEventCar) {
        UUID bookingId = kafkaEventCar.bookingId();//bookingId

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Аренда не найдена"));

        booking.setStatusBooking(StatusBooking.BOOKED);
//послать в кафку создание payment
        kafkaSenderBooking.createPayment(new KafkaEvent(booking.getCarId(), bookingId, null));

        return kafkaEventCar.carId();
    }
}
