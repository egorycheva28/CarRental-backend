package com.example.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Роль - это обязательное поле")
    @Column(name = "role", nullable = false)
    private Roles role;
}
