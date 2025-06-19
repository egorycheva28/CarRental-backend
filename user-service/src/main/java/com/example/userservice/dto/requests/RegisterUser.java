package com.example.userservice.dto.requests;

import com.example.userservice.model.Role;

import java.util.Set;

public record RegisterUser(
        String lastName,
        String firstName,
        String middleName,
        Long age,
        String phone,
        String email,
        String password,
        Set<Role> roles
) {
}
