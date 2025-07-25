package com.example.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User {

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

    @NotNull(message = "Это обязательное поле")
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @NotNull(message = "Фамилия - это обязательное поле")
    @Column(name = "last_name", nullable = false, length = 100)
    @Pattern(regexp = "^[А-ЯЁ][а-яё]{0,99}$", message = "В lastName допускаются только буквы русского алфавита, первая буква - заглавная, не более 100 символов")
    private String lastName;

    @NotNull(message = "Имя - это обязательное поле")
    @Column(name = "first_name", nullable = false, length = 100)
    @Pattern(regexp = "^[А-ЯЁ][а-яё]{0,99}$", message = "В firstName допускаются только буквы русского алфавита, первая буква - заглавная, не более 100 символов")
    private String firstName;

    @Column(name = "middle_name", length = 100)
    @Pattern(regexp = "^[А-ЯЁ][а-яё]{0,99}$", message = "В middleName допускаются только буквы русского алфавита, первая буква - заглавная, не более 100 символов")
    private String middleName;

    @NotNull(message = "Возраст - это обязательное поле")
    @Column(name = "age", nullable = false)
    private Long age;

    @NotNull(message = "Номер телефона - это обязательное поле")
    @Column(name = "phone", unique = true, nullable = false)
    @Pattern(regexp = "^7[0-9]{10}$", message = "Номер телефона должен состоять из 11 цифр и начинаться с 7.")
    private String phone;

    @NotNull(message = "Почта - это обязательное поле")
    @Column(name = "email", unique = true, nullable = false)
    @Email(message = "Email не соответствует стандартной маске почты")
    private String email;

    @NotNull(message = "Пароль - это обязательное поле")
    @Column(name = "password", nullable = false, length = 66)
    @Size(min = 6, max = 66, message = "Пароль должен содержать от 6 до 66")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}
