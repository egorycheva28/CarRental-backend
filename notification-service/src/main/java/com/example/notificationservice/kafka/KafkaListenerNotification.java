package com.example.notificationservice.kafka;

import com.example.notificationservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaListenerNotification {

    private final EmailService emailService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    //создание нового платежа на оплату
    @KafkaListener(topics = "createPaymentTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryPayment")
    public void createPayment(KafkaEvent kafkaEvent) {
        emailService.sendEmail(kafkaEvent.email(), "Создание нового платежа", "Платеж с ID: " + kafkaEvent.paymentId() + " создан!");
        simpMessagingTemplate.convertAndSend("/topic/notifications", "Платеж с ID: " + kafkaEvent.paymentId() + " создан!");
    }

    //оплата платежа
    @KafkaListener(topics = "doPaymentTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")
    public void doPayment(KafkaEvent kafkaEvent) {
        emailService.sendEmail(kafkaEvent.email(), "Оплата платежа", "Платеж с ID: " + kafkaEvent.paymentId() + " оплачен!");
        simpMessagingTemplate.convertAndSend("/topic/notifications", "Платеж с ID: " + kafkaEvent.paymentId() + " оплачен!");
    }

    //отмена платежа
    @KafkaListener(topics = "cancelPaymentTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")
    public void cancelPayment(KafkaEvent kafkaEvent) {
        emailService.sendEmail(kafkaEvent.email(), "Отмена платежа", "Платеж с ID: " + kafkaEvent.paymentId() + " отменено");
        simpMessagingTemplate.convertAndSend("/topic/notifications", "Платеж с ID: " + kafkaEvent.paymentId() + " отменено");
    }

    //резервирование бронирования
    @KafkaListener(topics = "reservedBookingTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryBooking")
    public void reservedBooking(KafkaEvent kafkaEvent) {
        emailService.sendEmail(kafkaEvent.email(), "Резервирование бронирования", "Бронирование с ID: " + kafkaEvent.paymentId() + " зарезервировано!");
        simpMessagingTemplate.convertAndSend("/topic/notifications", "Бронирование с ID: " + kafkaEvent.paymentId() + " зарезервировано!");
    }

    //создание бронирования
    @KafkaListener(topics = "createBookingTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryCar")
    public void createBooking(KafkaEvent kafkaEvent) {
        emailService.sendEmail(kafkaEvent.email(), "Создание бронирования", "Бронирование с ID: " + kafkaEvent.paymentId() + " создано!");
        simpMessagingTemplate.convertAndSend("/topic/notifications", "Бронирование с ID: " + kafkaEvent.paymentId() + " создано!");
    }

    //завершение аренды автомобиля
    @KafkaListener(topics = "completeBookingTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryCar")
    public void completeBooking(KafkaEvent kafkaEvent) {
        emailService.sendEmail(kafkaEvent.email(), "Завершение бронирования", "Бронирование с ID: " + kafkaEvent.paymentId() + " завершено!");
        simpMessagingTemplate.convertAndSend("/topic/notifications", "Бронирование с ID: " + kafkaEvent.paymentId() + " завершено!");
    }
}
