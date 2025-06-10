package com.example.dto.response;


import com.example.model.Role;
import com.example.model.Roles;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

public record GetUser(
        UUID id,
        String lastName,
        String firstName,
        String middleName,
        Long age,
        String phone,
        String email,
        Set<Role> roles
) {
}
