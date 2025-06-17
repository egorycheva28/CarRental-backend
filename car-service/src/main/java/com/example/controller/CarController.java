package com.example.controller;

import com.example.dto.requests.CreateCar;
import com.example.dto.requests.EditCar;
import com.example.dto.response.GetCar;
import com.example.dto.response.ListCars;
import com.example.dto.response.SuccessResponse;
import com.example.model.Status;
import com.example.service.CarService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping()
    public ListCars getCars(Status statusCar, Long size, Long current) {
        return carService.getCars(statusCar, size, current);
    }

    @GetMapping("/{id}")
    public GetCar getCarById(@PathVariable(name = "id") UUID carId) {
        return carService.getCarById(carId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public UUID createCar(Authentication authentication, @RequestBody CreateCar createCar) {
        return carService.createCar(authentication, createCar);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/edit/{id}")
    public SuccessResponse editCar(@PathVariable(name = "id") UUID carId, @RequestBody EditCar editCar) {
        return carService.editCar(carId, editCar);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public SuccessResponse statusRapair(@PathVariable(name = "id") UUID carId, Status status) {
        return carService.statusRapair(carId, status);
    }
}
