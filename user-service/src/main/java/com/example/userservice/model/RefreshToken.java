package com.example.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "refreshtoken")
@Data
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "Это обязательное поле")
    @Column(name = "create_date", nullable = false)
    private Date CreateDate = new Date();

    @NotNull(message = "Это обязательное поле")
    @Column(name = "token", nullable = false)
    private String token;

    //дата истечения токена
    @NotNull(message = "Это обязательное поле")
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @OneToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
