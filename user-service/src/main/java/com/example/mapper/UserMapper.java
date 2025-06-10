package com.example.mapper;

import com.example.dto.requests.RegisterUser;
import com.example.dto.response.GetUser;
import com.example.model.Role;
import com.example.model.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class UserMapper {

    public static User registerUser(RegisterUser registerUser) {
        User user = new User();
        user.setLastName(registerUser.lastName());
        user.setFirstName(registerUser.firstName());
        user.setMiddleName(registerUser.middleName());
        user.setAge(registerUser.age());
        user.setPhone(registerUser.phone());
        user.setEmail(registerUser.email());
        user.setPassword(registerUser.password());
        user.setRoles(registerUser.roles());
        return user;
    }

    public static GetUser getUser(User user) {
        return new GetUser(
                user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getAge(),
                user.getPhone(),
                user.getEmail(),
                user.getRoles()
        );
    }
}
