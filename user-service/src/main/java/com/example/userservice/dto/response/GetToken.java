package com.example.userservice.dto.response;

public record GetToken(
        String accessToken,
        String refreshToken
) {
}
