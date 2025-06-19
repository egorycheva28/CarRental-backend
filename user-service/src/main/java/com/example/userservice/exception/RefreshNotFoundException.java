package com.example.userservice.exception;

public class RefreshNotFoundException extends RuntimeException {
    public RefreshNotFoundException(String message) {
        super(message);
    }
}
