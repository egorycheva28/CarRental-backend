package com.example.carservice.service.impl;

import com.example.carservice.dto.Pagination;
import com.example.carservice.dto.requests.CreateCar;
import com.example.carservice.dto.requests.EditCar;
import com.example.carservice.dto.response.GetCar;
import com.example.carservice.dto.response.ListCars;
import com.example.carservice.dto.response.SuccessResponse;
import com.example.carservice.exception.CarNotFoundException;
import com.example.carservice.exception.PaginationException;
import com.example.carservice.model.Car;
import com.example.carservice.model.Status;
import com.example.carservice.repository.CarRepository;
import com.example.carservice.security.jwt.JwtUtils;
import com.example.carservice.service.CarService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.carservice.mapper.CarMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    public ListCars getCars(Status statusCar, Long size, Long current) {
        List<Car> cars = (List<Car>) carRepository.findAll();

        List<GetCar> filterCars = new ArrayList<>();//отфильтрованный список

        //фильтрация
        for (Car car : cars) {
            if (statusCar == null || statusCar.equals(car.getStatus())) {
                filterCars.add(CarMapper.getCar(car));
            }
        }

        //пагинация
        //size - количество на одной странице (размер страницы), count - всего страниц, current - текущая
        int countCars = filterCars.size();//всего переводов
        int countPage = (int) Math.ceil((double) countCars / size);//всего страниц

        if (countCars > 0 && current > countPage) {
            throw new PaginationException("Вы хотите посмотреть несуществующую страницу!");
        }

        List<GetCar> currentCars = filterCars.stream().skip((current - 1) * size).limit(size).toList(); //список переводов на данной странице

        Pagination pagination = new Pagination(size, countCars, current);
        return CarMapper.listCars(currentCars, pagination);
    }

    @Override
    public GetCar getCarById(UUID carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Такой машины нет!"));

        return CarMapper.getCar(car);
    }

    @Override
    public UUID createCar(Authentication authentication, CreateCar createCar) {
        String emailUser = JwtUtils.getUserEmailFromRequest(request);

        Car car = CarMapper.createCar(createCar);
        car.setEmailUser(emailUser);

        carRepository.save(car);
        return car.getId();
    }

    @Override
    public SuccessResponse editCar(UUID carId, EditCar editCar) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Такой машины нет!"));

        if (editCar.name() != null) {
            car.setName(editCar.name());
        }
        if (editCar.price() != null) {
            car.setPrice(editCar.price());
        }

        car.setEditDate(new Date());
        carRepository.save(car);

        return new SuccessResponse("Данные о машине успешно изменены!");
    }

    @Override
    public SuccessResponse statusRepair(UUID carId, Status status) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Такой машины нет!"));

        car.setStatus(status);
        car.setEditDate(new Date());
        carRepository.save(car);
        return new SuccessResponse("Статус машины успешно изменен!");
    }
}
