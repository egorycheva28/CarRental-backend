package com.example.carservice.mapper;

import com.example.carservice.dto.Pagination;
import com.example.carservice.dto.requests.CreateCar;
import com.example.carservice.dto.response.GetCar;
import com.example.carservice.dto.response.ListCars;
import com.example.carservice.model.Car;

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
