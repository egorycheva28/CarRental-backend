package com.example.mapper;

import com.example.dto.Pagination;
import com.example.dto.requests.CreateCar;
import com.example.dto.response.GetCar;
import com.example.dto.response.ListCars;
import com.example.model.Car;

import java.util.List;

public class CarMapper {

    public static Car createCar(CreateCar createCar) {
        Car car = new Car();
        car.setName(createCar.name());
        car.setStatus(createCar.status());
        car.setPrice(createCar.price());
        return car;
    }

    public static GetCar getCar(Car car) {
        return new GetCar(
                car.getId(),
                car.getName(),
                car.getStatus(),
                car.getPrice()
        );
    }

    public static ListCars listCars(List<GetCar> listCars, Pagination pagination) {
        return new ListCars(
                listCars,
                pagination
        );
    }

}
