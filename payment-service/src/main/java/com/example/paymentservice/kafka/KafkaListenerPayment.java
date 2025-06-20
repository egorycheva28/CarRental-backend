package com.example.paymentservice.kafka;

import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.StatusPayment;
import com.example.paymentservice.repository.PaymentRepository;
import com.example.paymentservice.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaListenerPayment {

    private final PaymentRepository paymentRepository;
    private final KafkaSenderPayment kafkaSenderPayment;

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerPayment.class);

    @KafkaListener(topics = "payment1-topik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryPayment")

    public void createPayment(KafkaEvent kafkaEvent) {

        Payment payment = new Payment();
        payment.setId(kafkaEvent.paymentId());
        payment.setStatusPayment(StatusPayment.NEW_PAYMENT);
        payment.setBookingId(kafkaEvent.bookingId());
        payment.setUserId(kafkaEvent.userId());

        paymentRepository.save(payment);
        logger.info("Платёж c ID: {} успешно сохранён", payment.getId());
    }

}
