package com.example.dto.response;

import com.example.dto.Pagination;

import java.util.List;

public record ListCars(
        List<GetCar> cars,
        Pagination pagination
) {
}
