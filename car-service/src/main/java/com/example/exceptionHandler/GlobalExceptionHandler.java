package com.example.exceptionHandler;

import com.example.exception.CarNotFoundException;
import com.example.exception.PaginationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = CarNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(CarNotFoundException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка получения пользователя");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = PaginationException.class)
    public ResponseEntity<Map<String, Object>> handlePaginationException(PaginationException ex) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("error", "Pagination Error");
        errorBody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(value = LoginException.class)
    public ResponseEntity<Map<String,Object>> handleLoginException(LoginException ex){
        Map<String,Object> errorbody = new HashMap<>();
        errorbody.put("error","Ошибка авторизации:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RegisterException.class)
    public ResponseEntity<Map<String,Object>> handleRegisterException(RegisterException ex){
        Map<String,Object> errorbody = new HashMap<>();
        errorbody.put("error","Ошибка регистрации:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.BAD_REQUEST);
    }*/
}
