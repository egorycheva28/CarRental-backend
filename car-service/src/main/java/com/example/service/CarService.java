package com.example.service;

import com.example.dto.requests.CreateCar;
import com.example.dto.requests.EditCar;
import com.example.dto.response.GetCar;
import com.example.dto.response.ListCars;
import com.example.dto.response.SuccessResponse;
import com.example.model.Status;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import java.util.UUID;

public interface CarService {
    ListCars getCars(Status statusCar, Long size, Long current);

    GetCar getCarById(UUID carId);

    UUID createCar(Authentication authentication, CreateCar createCar);

    SuccessResponse editCar(UUID carId, EditCar editCar);

    SuccessResponse statusRapair(UUID carId, Status status);
}
