package com.example.exception;

public class RegisterException extends RuntimeException {
    public RegisterException(String message) {
        super(message);
    }
}
