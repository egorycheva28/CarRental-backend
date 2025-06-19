package com.example.userservice.exceptionHandler;

import com.example.userservice.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка получения пользователя:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRoleNotFoundException(RoleNotFoundException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка получения роли:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RefreshNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleRefreshNotFoundException(RefreshNotFoundException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка при обновлении refresh token:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity<Map<String, Object>> handleLoginException(LoginException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка авторизации:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RegisterException.class)
    public ResponseEntity<Map<String, Object>> handleRegisterException(RegisterException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка регистрации:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка прав доступа:");
        errorbody.put("Эта функция доступна только админу", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RefreshException.class)
    public ResponseEntity<Map<String, Object>> handleRefreshException(RefreshException ex) {
        Map<String, Object> errorbody = new HashMap<>();
        errorbody.put("error", "Ошибка при обновлении refresh token:");
        errorbody.put("message", ex.getMessage());

        return new ResponseEntity<>(errorbody, HttpStatus.BAD_REQUEST);
    }

}
