package com.example.carservice.service;

import com.example.carservice.dto.requests.CreateCar;
import com.example.carservice.dto.requests.EditCar;
import com.example.carservice.dto.response.GetCar;
import com.example.carservice.dto.response.ListCars;
import com.example.carservice.dto.response.SuccessResponse;
import com.example.carservice.model.Status;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.UUID;

public interface CarService {
    ListCars getCars(Status statusCar, Long size, Long current);

    GetCar getCarById(UUID carId);

    UUID createCar(Authentication authentication, CreateCar createCar);

    SuccessResponse editCar(UUID carId, EditCar editCar);

    SuccessResponse statusRapair(UUID carId, Status status);
}
