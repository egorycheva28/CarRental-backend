package com.example.exception;

public class AvailabilityException extends RuntimeException {
    public AvailabilityException(String message) {
        super(message);
    }
}
