package com.example.service.impl;

import com.example.dto.Pagination;
import com.example.dto.requests.CreateBooking;
import com.example.dto.response.AvailabilityBookingResponse;
import com.example.dto.response.GetBooking;
import com.example.dto.response.ListBookings;
import com.example.dto.response.SuccessResponse;
import com.example.exception.PaginationException;
import com.example.mapper.BookingMapper;
import com.example.model.Booking;
import com.example.repository.BookingRepository;
//import com.example.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/*@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final CarRepository carRepository;
    private final BookingRepository bookingRepository;

    @Override
    public AvailabilityBookingResponse checkAvailability(UUID carId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Такой машины нет!"));

        if (car.getStatus().equals("FREE")) {
            return new AvailabilityBookingResponse(true);
        } else {
            return new AvailabilityBookingResponse(false);
        }
    }

    @Override
    public SuccessResponse changeStatusBooking(UUID carId, AvailabilityBookingResponse availabilityBookingResponse) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException("Такой машины нет!"));

        if (availabilityBookingResponse.availability().equals(false)) {
            throw new AvailabilityException("Автомобиль не свободен");
        }

        car.setStatus(Status.BOOKED);
        carRepository.save(car);
        return new SuccessResponse("Статус успешно сменен на 'Забронировано'");
    }

    @Override
    public UUID createBooking(CreateBooking createBooking) {
        Booking booking = BookingMapper.createBooking(createBooking);

        bookingRepository.save(booking);
        return booking.getId();
    }

    @Override
    public SuccessResponse completeBooking(UUID bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Аренда не найдена"));
        booking.setEndTime(LocalDateTime.now());
        booking.setStatus("Завершено");
        bookingRepository.save(booking);

        // Обновить статус автомобиля
        //Car car = booking.getCar();
        //car.setStatus(Status.FREE);
        //carRepository.save(car);
        return new SuccessResponse("Аренда успешно завершена!");
    }*/

    //все
    /*@Override
    public ListBookings getCarHistory(Authentication authentication, Long size, Long
            current) {
        List<Booking> payments = (List<Booking>) bookingRepository.findAll();

        List<GetBooking> filterPayments = new ArrayList<>();//отфильтрованный список

        //фильтрация
        for (Booking booking : bookings) {
            if (statusPayment == null || statusPayment.equals(booking.getStatus())) {
                filterPayments.add(PaymentMapper.getPayment(payment));
            }
        }

        //пагинация
        //size - количество на одной странице (размер страницы), count - всего страниц, current - текущая
        int countPayments = filterPayments.size();//всего переводов
        int countPage = (int) Math.ceil((double) countPayments / size);//всего страниц

        if (countPayments > 0 && current > countPage) {
            throw new PaginationException("Вы хотите посмотреть несуществующую страницу!");
        }

        List<GetBooking> currentPayments = filterPayments.stream().skip((current - 1) * size).limit(size).toList(); //список переводов на данной странице

        Pagination pagination = new Pagination(size, countPayments, current);
        return BookingMapper.listBookings(currentPayments, pagination);
    }*/

    //}
