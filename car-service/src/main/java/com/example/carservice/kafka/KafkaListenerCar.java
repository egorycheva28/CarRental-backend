package com.example.carservice.kafka;

import com.example.carservice.model.Car;
import com.example.carservice.model.Status;
import com.example.carservice.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KafkaListenerCar {

    private final CarRepository carRepository;
    private final KafkaSenderCar kafkaSenderCar;
    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerCar.class);

    @KafkaListener(topics = "booking-topik", groupId = "car-group", containerFactory = "kafkaListenerContainerFactoryCar")

    public void createBooking(KafkaEvent kafkaEvent) {
        UUID carId = kafkaEvent.carId();
        logger.info("Создается бронирование для автомобиля с ID: {}", carId);

        try {
            Optional<Car> optionalCar = carRepository.findById(carId);
            if (!optionalCar.isPresent()) {
                logger.warn("Автомобиль с ID {} не найден.", carId);
                return;
            }

            Car car = optionalCar.get();
            car.setStatus(Status.BOOKED);
            carRepository.save(car);
            logger.info("Статус автомобиля {} обновлен на {}", carId, car.getStatus());

            kafkaSenderCar.reservedCarEvent(new KafkaEvent(carId, kafkaEvent.bookingId(), null));
            logger.info("Отправлено событие о бронировании для автомобиля с ID: {}", carId);

        } catch (Exception e) {
            logger.error("Ошибка при создании бронирования для автомобиля с ID: {}", carId, e);
        }
    }

    /*public void createBooking(Event event) {
        UUID carId = event.carId();

        Optional<Car> optionalCar = carRepository.findById(carId);

        Car car = optionalCar.get();
        car.setStatus(Status.BOOKED);
        carRepository.save(car);
        System.out.println(car.getStatus());
        kafkaSenderCar.reservedCarEvent(new Event(carId));
    }*/


}
