package com.example.carservice.dto.response;

import com.example.carservice.dto.Pagination;

import java.util.List;

public record ListCars(
        List<GetCar> cars,
        Pagination pagination
) {
}
