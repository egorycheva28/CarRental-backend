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

    @KafkaListener(topics = "booking1-topik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")

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

        kafkaSenderBooking.createPayment(new KafkaEvent(kafkaEvent.carId(), bookingId, kafkaEvent.paymentId(), kafkaEvent.userId()));
        logger.info("Создано событие оплаты для бронирования ID {} и paymentId {}", bookingId, kafkaEvent.paymentId());

        logger.info("Процесс резервирования завершен для carId: {}", kafkaEvent.carId());
        return kafkaEvent.carId();
    }

    @KafkaListener(topics = "payment2-topik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")

    public UUID cancelPayment(KafkaEvent kafkaEvent) {
        UUID bookingId = kafkaEvent.bookingId();

        logger.info("Начинаем отмену оплаты для_bookingId={}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> {
                    logger.error("Бронирование не найдено для bookingId={}", bookingId);
                    return new BookingNotFoundException("Аренда не найдена");
                });

        logger.info("Обновляем статус бронирования для bookingId={}", bookingId);
        booking.setStatusBooking(StatusBooking.CANCELLED);
        bookingRepository.save(booking);
        logger.info("Статус бронирования обновлён на {} для bookingId={}", StatusBooking.CANCELLED, bookingId);

        logger.info("Отправляем сообщение автомобилю об отмене платежа для bookingId={}", bookingId);
        kafkaSenderBooking.cancelPayment(new KafkaEvent(booking.getCarId(), bookingId, kafkaEvent.paymentId(), kafkaEvent.userId()));

        logger.info("Обработка отмены платежа завершена для bookingId={}", bookingId);
        return kafkaEvent.bookingId();

    }
}
