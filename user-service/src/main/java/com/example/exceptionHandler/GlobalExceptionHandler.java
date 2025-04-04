package com.example.exceptionHandler;

import com.example.exception.LoginException;
import com.example.exception.RegisterException;
import com.example.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleUserNotFoundException(UserNotFoundException ex){
        Map<String,Object> errorbody = new HashMap<>();
        errorbody.put("error","dd");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = LoginException.class)
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
    }
}
