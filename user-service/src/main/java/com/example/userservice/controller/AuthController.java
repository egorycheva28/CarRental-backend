package com.example.userservice.controller;

import com.example.userservice.dto.requests.LoginUser;
import com.example.userservice.dto.requests.RefreshTokenRequest;
import com.example.userservice.dto.requests.RegisterUser;
import com.example.userservice.dto.response.GetToken;
import com.example.userservice.dto.response.SuccessResponse;
import com.example.userservice.service.impl.RefreshTokenService;
import com.example.userservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/")
@RequiredArgsConstructor
@Tag(name = "Auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("register")
    @Operation(summary = "Регистрация пользователей в системе")
    public GetToken registerUser(@RequestBody RegisterUser data) {
        return authService.registerUser(data);
    }

    @PostMapping("login")
    @Operation(summary = "Вход пользователя в систему")
    public GetToken loginUser(@RequestBody LoginUser data) {
        return authService.loginUser(data);
    }

    @PostMapping("logout")
    @Operation(summary = "Выход пользователя из системы")
    public SuccessResponse logoutUser(Authentication authentication) {
        return authService.logoutUser(authentication);
    }

    @PostMapping("refreshToken")
    @Operation(summary = "Обновление токенов")
    public GetToken refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }
}