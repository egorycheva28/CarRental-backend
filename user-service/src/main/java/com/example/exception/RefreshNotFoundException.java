package com.example.exception;

public class RefreshNotFoundException extends RuntimeException {
    public RefreshNotFoundException(String message) {
        super(message);
    }
}
