package com.example.paymentservice.exception;

public class PaginationException extends RuntimeException {
    public PaginationException(String message) {
        super(message);
    }
}
