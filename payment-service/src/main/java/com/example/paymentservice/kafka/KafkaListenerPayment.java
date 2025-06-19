package com.example.paymentservice.kafka;

import com.example.paymentservice.model.Payment;
import com.example.paymentservice.model.StatusPayment;
import com.example.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaListenerPayment {

    private final PaymentRepository paymentRepository;
    private final KafkaSenderPayment kafkaSenderPayment;

    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerPayment.class);

    @KafkaListener(topics = "booking-topik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryPayment")

    public void createPayment(KafkaEvent kafkaEvent) {
        UUID carId = kafkaEvent.carId();
        logger.info("Создание платежа: начато. CarId: {}", carId);

        Payment payment = new Payment();
        payment.setStatusPayment(StatusPayment.NEW_PAYMENT);
        payment.setBookingId(carId);
        payment.setUserId(carId);

        logger.debug("Объект Payment перед сохранением: {}", payment);
        paymentRepository.save(payment);
        logger.info("Платёж успешно сохранён. PaymentId: {}", payment.getId());
    }

    /*public void listen(Event event) {
        UUID carId = event.carId();
        //UUID bookingId=event.id();

        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isEmpty()) {
            kafkaSender.
        }
        //kafkaSender.processNotification(message);
    }*/

    /*public void createPayment(KafkaEvent kafkaEvent) {
        UUID carId = kafkaEvent.carId();

        Payment payment = new Payment();
        payment.setStatusPayment(StatusPayment.NEW_PAYMENT);
        payment.setBookingId(carId);
        payment.setUserId(carId);

        paymentRepository.save(payment);

    }*/
}
