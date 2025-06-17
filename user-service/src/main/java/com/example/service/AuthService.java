package com.example.service;

import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RefreshTokenRequest;
import com.example.dto.requests.RegisterUser;
import com.example.dto.response.GetToken;
import com.example.dto.response.SuccessResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    GetToken registerUser(RegisterUser data);

    GetToken loginUser(LoginUser data);

    SuccessResponse logoutUser(Authentication authentication);

    GetToken refreshToken(RefreshTokenRequest refreshTokenRequest);
}
