package com.example.userservice.dto.requests;

public record EditUser(
        String lastName,
        String firstName,
        String middleName,
        Long age,
        String phoneNumber,
        String email
) {
}
