package com.example.paymentservice.kafka;

import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.StatusPayment;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class KafkaListenerPayment {

    private final PaymentRepository paymentRepository;

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerPayment.class);

    @KafkaListener(topics = "createPaymentTopik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryPayment")

    public void createPayment(KafkaEvent kafkaEvent) {

        Payment payment = new Payment();
        payment.setId(kafkaEvent.paymentId());
        payment.setStatusPayment(StatusPayment.NEW_PAYMENT);
        payment.setBookingId(kafkaEvent.bookingId());
        payment.setUserId(kafkaEvent.userId());

        paymentRepository.save(payment);
        logger.info("Платёж c ID: {} успешно сохранён", payment.getId());
    }

    @KafkaListener(topics = "cancelBookingTopik1", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryPayment")

    public void cancelBooking(KafkaEvent kafkaEvent) {
        try {
            Optional<Payment> optionalPayment = paymentRepository.findById(kafkaEvent.paymentId());
            if (!optionalPayment.isPresent()) {
                logger.warn("Платеж с ID {} не найден.", kafkaEvent.paymentId());
                return;
            }

            Payment payment = optionalPayment.get();
            payment.setStatusPayment(StatusPayment.CANCELLED);
            paymentRepository.save(payment);

            logger.info("Платёж c ID: {} успешно отменен", payment.getId());
        } catch (Exception e) {
            logger.error("Ошибка при отмене платежа с ID: {}", kafkaEvent.paymentId(), e);
        }
    }

}
