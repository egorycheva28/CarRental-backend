package com.example.controller;

import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RefreshTokenRequest;
import com.example.dto.requests.RegisterUser;
import com.example.dto.response.GetToken;
import com.example.dto.response.SuccessResponse;
import com.example.security.services.RefreshTokenService;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("register")
    public UUID registerUser(@RequestBody RegisterUser data) {
        return authService.registerUser(data);
    }

    @PostMapping("login")
    public String loginUser(@RequestBody LoginUser data) {
        return authService.loginUser(data);
    }

    @PostMapping("logout")
    public SuccessResponse logoutUser(Authentication authentication) {
        return authService.logoutUser(authentication);
    }

    /*@PostMapping("refreshToken")
    public GetToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshToken(refreshTokenRequest);
    }*/
}