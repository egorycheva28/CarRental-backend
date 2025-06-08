package com.example.dto.response;

public record GetToken(
        String accessToken,
        String refreshToken
) {
}
