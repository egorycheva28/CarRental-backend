package com.example.bookingservice.exceptionHandler;

import com.example.bookingservice.exception.CarNotFoundException;
import com.example.bookingservice.exception.CarStatusException;
import com.example.bookingservice.exception.PaginationException;
import com.example.bookingservice.exception.BookingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BookingNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleBookingNotFoundException(BookingNotFoundException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка получения аренды автомобиля:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CarNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCarNotFoundException(CarNotFoundException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка получения автомобиля:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = CarStatusException.class)
    public ResponseEntity<Map<String, Object>> handleCarStatusException(CarStatusException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка бронирования автомобиля:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PaginationException.class)
    public ResponseEntity<Map<String, Object>> handlePaginationException(PaginationException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", "Pagination Error:");
        errorBody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }
}
