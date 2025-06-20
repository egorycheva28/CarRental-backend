package com.example.carservice.controller;

import com.example.carservice.dto.requests.CreateCar;
import com.example.carservice.dto.requests.EditCar;
import com.example.carservice.dto.response.GetCar;
import com.example.carservice.dto.response.ListCars;
import com.example.carservice.dto.response.SuccessResponse;
import com.example.carservice.model.Status;
import com.example.carservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("car")
@RequiredArgsConstructor
@Tag(name = "Car")
public class CarController {

    private final CarService carService;

    @GetMapping()
    @Operation(summary = "Просмотр информации об автомобиле")
    public ListCars getCars(@RequestParam(value = "statusCar", required = false) Status statusCar, Long size, Long current) {
        return carService.getCars(statusCar, size, current);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Просмотр информации об автомобиле по id")
    public GetCar getCarById(@PathVariable(name = "id") UUID carId) {
        return carService.getCarById(carId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "Добавление автомобиля (для админа)")
    public UUID createCar(Authentication authentication, @RequestBody CreateCar createCar) {
        return carService.createCar(authentication, createCar);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/edit/{id}")
    @Operation(summary = "Редактирование информации об автомобиле (для админа)")
    public SuccessResponse editCar(@PathVariable(name = "id") UUID carId, @RequestBody EditCar editCar) {
        return carService.editCar(carId, editCar);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Смена статуса автомобиля на 'на рамонте' (для админа)")
    public SuccessResponse statusRapair(@PathVariable(name = "id") UUID carId, Status status) {
        return carService.statusRepair(carId, status);
    }
}
