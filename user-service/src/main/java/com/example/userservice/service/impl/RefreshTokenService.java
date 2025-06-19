package com.example.userservice.service.impl;

import com.example.userservice.dto.requests.RefreshTokenRequest;
import com.example.userservice.dto.response.GetToken;
import com.example.userservice.exception.RefreshException;
import com.example.userservice.exception.RefreshNotFoundException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.model.RefreshToken;
import com.example.userservice.model.User;
import com.example.userservice.repository.RefreshTokenRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private int jwtRefreshExpirationMs = 86400000;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public GetToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return findByToken(refreshTokenRequest.refreshToken())
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String accessToken = jwtUtils.generateTokenFromUser(user);
                    //RefreshToken refreshToken = createRefreshToken(user.getId());
                    return new GetToken(accessToken, refreshTokenRequest.refreshToken());
                })
                .orElseThrow(() -> new RefreshNotFoundException("Такого refresh token нет в базе!"));
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshException("Refresh token истек. Пожалуйста обновите токен!");
        }

        return token;
    }

    @Transactional
    public void deleteByUserId(UUID userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}