package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.CarDto;
import com.example.bookingservice.dto.Pagination;
import com.example.bookingservice.dto.Status;
import com.example.bookingservice.dto.requests.CreateBooking;
import com.example.bookingservice.dto.response.AvailabilityBookingResponse;
import com.example.bookingservice.dto.response.GetBooking;
import com.example.bookingservice.dto.response.ListBookings;
import com.example.bookingservice.dto.response.SuccessResponse;
import com.example.bookingservice.exception.BookingNotFoundException;
import com.example.bookingservice.exception.CarStatusException;
import com.example.bookingservice.exception.PaginationException;
import com.example.bookingservice.kafka.KafkaEvent;
import com.example.bookingservice.kafka.KafkaSenderBooking;
import com.example.bookingservice.mapper.BookingMapper;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.model.StatusBooking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.security.jwt.JwtUtils;
import com.example.bookingservice.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final KafkaSenderBooking kafkaSenderBooking;
    private final CarServiceFake carServiceFake;

    @Autowired
    private HttpServletRequest request;

    @Override
    public AvailabilityBookingResponse checkAvailability(UUID carId) {
        CarDto carDto = carServiceFake.getCarById(carId);

        if (carDto.status().equals(Status.FREE)) {
            return new AvailabilityBookingResponse(true);
        }
        return new AvailabilityBookingResponse(false);
    }

    @Override
    public UUID createBooking(CreateBooking createBooking) {
        UUID userId = JwtUtils.getUserIdFromRequest(request);
        String email = JwtUtils.getUserEmailFromRequest(request);

        CarDto carDto = carServiceFake.getCarById(createBooking.carId());

        if (carDto.status() == Status.BOOKED) {
            throw new CarStatusException("Данная машина уже забронирована!");
        }
        if (carDto.status() == Status.UNDER_REPAIR) {
            throw new CarStatusException("Данная машина на ремонте!");
        }

        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setCarId(createBooking.carId());
        booking.setStatusBooking(StatusBooking.NEW);
        booking.setPaymentId(UUID.randomUUID());

        bookingRepository.save(booking);

        kafkaSenderBooking.createBooking(new KafkaEvent(booking.getCarId(), booking.getId(), booking.getPaymentId(), userId, email));

        return booking.getId();
    }

    @Override
    public SuccessResponse completeBooking(UUID bookingId) {
        UUID userId = JwtUtils.getUserIdFromRequest(request);
        String email = JwtUtils.getUserEmailFromRequest(request);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Аренда не найдена!"));

        if (!booking.getStatusBooking().equals(StatusBooking.RENTED)) {
            throw new RuntimeException("Эта бронь не оплачена!");
        }

        booking.setStatusBooking(StatusBooking.COMPLETED);
        booking.setEditDate(LocalDateTime.now());
        bookingRepository.save(booking);

        kafkaSenderBooking.completeBooking(new KafkaEvent(booking.getCarId(), bookingId, null, userId, email));

        return new SuccessResponse("Аренда успешно завершена!");
    }

    @Scheduled(fixedRate = 600000)
    public void cancelBooking() {
        UUID userId = JwtUtils.getUserIdFromRequest(request);
        String email = JwtUtils.getUserEmailFromRequest(request);

        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (Booking booking : bookings) {
            if (!booking.getStatusBooking().equals(StatusBooking.RENTED)) {
                LocalDateTime createdDate = booking.getCreateDate();
                Duration duration = Duration.between(createdDate, now);

                if (duration.toMinutes() >= 10) {
                    booking.setStatusBooking(StatusBooking.CANCELLED);
                    bookingRepository.save(booking);

                    kafkaSenderBooking.cancelBooking1(new KafkaEvent(booking.getCarId(), booking.getId(), booking.getPaymentId(), userId, email));
                    kafkaSenderBooking.cancelBooking2(new KafkaEvent(booking.getCarId(), booking.getId(), booking.getPaymentId(), userId, email));
                }
            }
        }
    }

    @Override
    public ListBookings getUserBookingHistory(Long size, Long current) {
        UUID userId = JwtUtils.getUserIdFromRequest(request);

        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();

        List<GetBooking> filterBookings = new ArrayList<>();//отфильтрованный список

        //фильтрация
        for (Booking booking : bookings) {
            if (userId == null || userId.equals(booking.getUserId())) {
                filterBookings.add(BookingMapper.getBooking(booking));
            }
        }

        //пагинация
        //size - количество на одной странице (размер страницы), count - всего страниц, current - текущая
        int countBookings = filterBookings.size();//всего переводов
        int countPage = (int) Math.ceil((double) countBookings / size);//всего страниц

        if (countBookings > 0 && current > countPage) {
            throw new PaginationException("Вы хотите посмотреть несуществующую страницу!");
        }

        List<GetBooking> currentBookings = filterBookings.stream().skip((current - 1) * size).limit(size).toList(); //список переводов на данной странице

        Pagination pagination = new Pagination(size, countBookings, current);
        return BookingMapper.listBookings(currentBookings, pagination);
    }

    @Override
    public ListBookings getBookingHistory(UUID userId, UUID carId, Long size, Long current) {
        List<Booking> bookings = (List<Booking>) bookingRepository.findAll();

        List<GetBooking> filterBookings = new ArrayList<>();//отфильтрованный список

        //фильтрация
        for (Booking booking : bookings) {
            if (userId != null && carId != null) {
                if (userId.equals(booking.getUserId()) && carId.equals(booking.getCarId())) {
                    filterBookings.add(BookingMapper.getBooking(booking));
                }
            } else if (userId == null && (carId == null || carId.equals(booking.getCarId()))) {
                filterBookings.add(BookingMapper.getBooking(booking));
            } else if (carId == null && userId.equals(booking.getUserId())) {
                filterBookings.add(BookingMapper.getBooking(booking));
            }
        }

        //пагинация
        //size - количество на одной странице (размер страницы), count - всего страниц, current - текущая
        int countBookings = filterBookings.size();//всего переводов
        int countPage = (int) Math.ceil((double) countBookings / size);//всего страниц

        if (countBookings > 0 && current > countPage) {
            throw new PaginationException("Вы хотите посмотреть несуществующую страницу!");
        }

        List<GetBooking> currentBookings = filterBookings.stream().skip((current - 1) * size).limit(size).toList(); //список переводов на данной странице

        Pagination pagination = new Pagination(size, countBookings, current);
        return BookingMapper.listBookings(currentBookings, pagination);
    }
}