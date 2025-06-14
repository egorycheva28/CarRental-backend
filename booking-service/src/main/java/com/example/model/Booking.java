package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Data
public class Booking {

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

    @NotNull(message = "Цена - это обязательное поле")
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @NotNull(message = "Цена - это обязательное поле")
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    /*@NotNull(message = "Цена - это обязательное поле")
    @Column(name = "end_time", nullable = false)
    private String status;*/

    //кто арендовал машину
    /*@ManyToOne
    @JoinColumn(name="id", nullable = false)
    private User user1;*/

    //какую арендовал машину
    /*@ManyToOne
    @JoinColumn(name="id", nullable = false)
    private User user1;*/
}

