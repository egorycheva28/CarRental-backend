package com.example.userservice.dto.requests;

public record LoginUser(
        String email,
        String password
) {
}
