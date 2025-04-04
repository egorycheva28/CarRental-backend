package com.example.dto.requests;

public record EditUser(
        String lastName,
        String firstName,
        String middleName,
        Long age,
        String phoneNumber,
        String email
) {
}
