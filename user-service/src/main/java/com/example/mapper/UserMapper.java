package com.example.mapper;

import com.example.dto.requests.RegisterUser;
import com.example.dto.response.GetUser;
import com.example.model.User;

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

        return user;
    }

    public static GetUser getUser(User user) {
        return new GetUser(
                user.getId(),
                user.getCreateDate(),
                user.getEditDate(),
                user.getLastName(),
                user.getFirstName(),
                user.getMiddleName(),
                user.getAge(),
                user.getPhone(),
                user.getEmail()
        );
    }
}
