package com.example.service.impl;

import com.example.dto.Pagination;
import com.example.dto.requests.CreateCar;
import com.example.dto.requests.EditCar;
import com.example.dto.response.GetCar;
import com.example.dto.response.ListCars;
import com.example.dto.response.SuccessResponse;
import com.example.exception.CarNotFoundException;
import com.example.exception.PaginationException;
import com.example.model.Car;
import com.example.model.Status;
import com.example.repository.CarRepository;
import com.example.service.CarService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;
import com.example.mapper.CarMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    //все
    @Override
    public ListCars getCars(Authentication authentication, Status statusCar, Long size, Long current) {
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

    //все
    // @Override
    //public String getAvailableCars() {

    //}

    //все
    @Override
    public GetCar getCarById(UUID carId, Authentication authentication) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Такой машины нет!"));

        return CarMapper.getCar(car);
    }

    //админ
    @Override
    public UUID createCar(CreateCar createCar) {

        Car car = CarMapper.createCar(createCar);
        /*var errors = validator.validate(car);
        if (!errors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();

            for (var error : errors) {
                errorMessage.append(error.getMessage()).append(" ");
            }

            throw new ValidationException(errorMessage.toString());
        }*/

        carRepository.save(car);
        return car.getId();
    }

    //админ
    @Override
    public SuccessResponse editCar(UUID carId, EditCar editCar, Authentication authentication) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID: " + carId + " not found."));

        //название и цену редактировать
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

    //админ
    @Override
    public SuccessResponse statusRapair(UUID carId, Status status) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Car with ID: " + carId + " not found."));

        car.setStatus(status);
        car.setEditDate(new Date());
        carRepository.save(car);
        return new SuccessResponse("Статус машины успешно изменен!");
    }
}
