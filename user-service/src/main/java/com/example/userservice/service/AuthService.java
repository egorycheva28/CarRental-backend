package com.example.userservice.service;

import com.example.userservice.dto.requests.LoginUser;
import com.example.userservice.dto.requests.RefreshTokenRequest;
import com.example.userservice.dto.requests.RegisterUser;
import com.example.userservice.dto.response.GetToken;
import com.example.userservice.dto.response.SuccessResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    GetToken registerUser(RegisterUser data);

    GetToken loginUser(LoginUser data);

    SuccessResponse logoutUser(Authentication authentication);

    GetToken refreshToken(RefreshTokenRequest refreshTokenRequest);
}
