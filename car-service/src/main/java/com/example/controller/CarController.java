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
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("car")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    //все
    @GetMapping()
    public ListCars getCars(Authentication authentication, Status statusCar, Long size, Long current) {
        return carService.getCars(authentication, statusCar, size, current);
    }

    //все
    @GetMapping("/{id}")
    public GetCar getCarById(@PathVariable(name = "id") UUID carId, Authentication authentication) {
        return carService.getCarById(carId, authentication);
    }

    //админ
    @PostMapping("/create")
    public UUID createCar(@RequestBody  CreateCar createCar) {
        return carService.createCar(createCar);
    }

    //админ
    @PatchMapping("/edit/{id}")
    public SuccessResponse editCar(@PathVariable(name = "id") UUID carId, @RequestBody EditCar editCar, Authentication authentication) {
        return carService.editCar(carId, editCar, authentication);
    }

    //админ
    @PutMapping("/{id}")
    public SuccessResponse statusRapair(@PathVariable(name = "id") UUID carId, Status status) {
        return carService.statusRapair(carId, status);
    }
}
