package com.example.service;

import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RegisterUser;
import com.example.dto.response.SuccessResponse;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface AuthService {
    UUID registerUser(RegisterUser data);

    String loginUser(LoginUser data);

    SuccessResponse logoutUser(Authentication authentication);
}
