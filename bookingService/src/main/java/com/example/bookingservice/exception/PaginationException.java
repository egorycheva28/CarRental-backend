package com.example.bookingservice.exception;

public class PaginationException extends RuntimeException {
    public PaginationException(String message) {
        super(message);
    }
}
