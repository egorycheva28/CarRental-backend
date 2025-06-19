package com.example.userservice.mapper;

import com.example.userservice.dto.requests.RegisterUser;
import com.example.userservice.dto.response.GetUser;
import com.example.userservice.model.User;

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
                user.isActive(),
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
