package com.example.paymentservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
public class Payment {

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
    @NotNull(message = "Статус оплаты - это обязательное поле")
    @Column(name = "status_payment", nullable = false)
    private StatusPayment statusPayment;

    @NotNull(message = "UserId - это обязательное поле")
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @NotNull(message = "BookingId - это обязательное поле")
    @Column(name = "booking_id", nullable = false)
    private UUID bookingId;
}

