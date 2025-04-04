package com.example.dto.requests;

import com.example.model.Role;
import com.example.model.Roles;

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
