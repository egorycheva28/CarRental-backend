package com.example.carservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "cars")
@Data
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @NotNull(message = "Это обязательное поле")
    @Column(name = "create_date", nullable = false)
    private Date createDate = new Date();

    @NotNull(message = "Это обязательное поле")
    @Column(name = "edit_date", nullable = false)
    private Date editDate = new Date();

    @NotNull(message = "Название автомобиля - это обязательное поле")
    @Column(name = "name", nullable = false, length = 100)
    @Pattern(regexp = "^[А-ЯЁ][а-яё]{0,99}$", message = "В lastName допускаются только буквы русского алфавита, первая буква - заглавная, не более 100 символов")
    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Статус - это обязательное поле")
    @Column(name = "status", nullable = false)
    private Status status;

    @NotNull(message = "Цена - это обязательное поле")
    @Column(name = "price", nullable = false)
    private Long price;

    @NotNull(message = "Почта пользователя - это обязательное поле")
    @Column(name = "email_user", nullable = false)
    @Email(message = "Email не соответствует стандартной маске почты")
    private String emailUser;
}
