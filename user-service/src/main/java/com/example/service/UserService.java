package com.example.service;

import com.example.dto.requests.EditUser;
import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RegisterUser;
import com.example.dto.response.GetToken;
import com.example.dto.response.GetUser;
import com.example.model.User;

import java.util.UUID;

public interface UserService {
    UUID registerUser(RegisterUser data);
    GetToken loginuser(LoginUser data);
    String logoutUser();
    User getProfile(UUID id);
    GetUser getProfileById(UUID id);
    String deleteUser(UUID id);
    String editUser(EditUser data, UUID id);
}
