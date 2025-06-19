package com.example.bookingservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "Это обязательное поле")
    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @NotNull(message = "Это обязательное поле")
    @Column(name = "edit_date", nullable = false)
    private LocalDateTime editDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Статус бронирования - это обязательное поле")
    @Column(name = "status_booking", nullable = false)
    private StatusBooking statusBooking;

    @NotNull(message = "UserId - это обязательное поле")
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotNull(message = "CarId - это обязательное поле")
    @Column(name = "car_id", nullable = false)
    private UUID carId;

    @NotNull(message = "PaymentId - это обязательное поле")
    @Column(name = "payment_id", nullable = false)
    private UUID paymentId;
}

