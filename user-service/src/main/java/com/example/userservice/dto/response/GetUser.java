package com.example.userservice.dto.response;


import com.example.userservice.model.Role;

import java.util.Set;
import java.util.UUID;

public record GetUser(
        UUID id,
        Boolean isActive,
        String lastName,
        String firstName,
        String middleName,
        Long age,
        String phone,
        String email,
        Set<Role> roles
) {
}
