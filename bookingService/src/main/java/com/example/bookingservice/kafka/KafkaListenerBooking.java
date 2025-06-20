package com.example.bookingservice.kafka;

import com.example.bookingservice.exception.BookingNotFoundException;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.StatusBooking;
import com.example.bookingservice.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaListenerBooking {

    private final KafkaSenderBooking kafkaSenderBooking;
    private final BookingRepository bookingRepository;
    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerBooking.class);

    @KafkaListener(topics = "reservedBookingTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")

    public UUID reservedBooking(KafkaEvent kafkaEvent) {
        logger.info("Начинается процесс резервирования бронирования для event: {}", kafkaEvent);

        UUID bookingId = kafkaEvent.bookingId();

        logger.debug("Получен bookingId: {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.error("Бронирование с ID {} не найдено", bookingId);
                    return new BookingNotFoundException("Аренда не найдена");
                });

        logger.info("Обработка бронирования: ID = {}, текущий статус = {}", bookingId, booking.getStatusBooking());

        booking.setStatusBooking(StatusBooking.BOOKED);
        bookingRepository.save(booking);

        logger.info("Обновлен статус бронирования ID {} на {}", bookingId, StatusBooking.BOOKED);

        kafkaSenderBooking.createPayment(new KafkaEvent(kafkaEvent.carId(), bookingId, kafkaEvent.paymentId(), kafkaEvent.userId(), kafkaEvent.email()));
        logger.info("Создано событие оплаты для бронирования ID {} и paymentId {}", bookingId, kafkaEvent.paymentId());

        logger.info("Процесс резервирования завершен для carId: {}", kafkaEvent.carId());
        return kafkaEvent.carId();
    }

    @KafkaListener(topics = "cancelPaymentTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")

    public UUID cancelPayment(KafkaEvent kafkaEvent) {
        UUID bookingId = kafkaEvent.bookingId();

        logger.info("Начинаем отмену оплаты для бронирования с ID {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.error("Бронирование с ID {} не найдено", bookingId);
                    return new BookingNotFoundException("Аренда не найдена");
                });

        logger.info("Обновляем статус бронирования ID {}", bookingId);
        booking.setStatusBooking(StatusBooking.CANCELLED);
        bookingRepository.save(booking);
        logger.info("Обновлен статус бронирования ID {} на {}", bookingId, StatusBooking.CANCELLED);

        logger.info("Отправляем сообщение автомобилю об отмене платежа для бронирования с ID {}", bookingId);
        kafkaSenderBooking.cancelPayment(new KafkaEvent(booking.getCarId(), bookingId, kafkaEvent.paymentId(), kafkaEvent.userId(), kafkaEvent.email()));

        logger.info("Обработка отмены платежа завершена для бронирования с ID {}", bookingId);
        return kafkaEvent.bookingId();
    }

    @KafkaListener(topics = "doPaymentTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")

    public UUID doPayment(KafkaEvent kafkaEvent) {
        UUID bookingId = kafkaEvent.bookingId();

        logger.info("Начинаем аренду для бронирования с ID {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.error("Бронирование с ID {} не найдено", bookingId);
                    return new BookingNotFoundException("Аренда не найдена");
                });

        logger.info("Обновляем статус бронирования ID {}", bookingId);
        booking.setStatusBooking(StatusBooking.RENTED);
        bookingRepository.save(booking);
        logger.info("Обновлен статус бронирования ID {} на {}", bookingId, StatusBooking.RENTED);

        logger.info("Отправляем сообщение автомобилю об аренде для бронирования c ID{}", bookingId);
        kafkaSenderBooking.doPayment(new KafkaEvent(booking.getCarId(), bookingId, kafkaEvent.paymentId(), kafkaEvent.userId(), kafkaEvent.email()));

        logger.info("Обработка аренды завершена для бронирования с ID{}", bookingId);
        return kafkaEvent.bookingId();

    }
}
