package com.example.security.services;

import com.example.dto.requests.RefreshTokenRequest;
import com.example.dto.response.GetToken;
import com.example.exception.RefreshException;
import com.example.exception.RefreshNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.model.RefreshToken;
import com.example.model.User;
import com.example.repository.RefreshTokenRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
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

    //@Transactional
    public RefreshToken createRefreshToken(UUID userId) {

        System.out.println("123");
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        System.out.println("456");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        System.out.println("789");
        refreshToken.setExpiryDate(Instant.now().plusMillis(jwtRefreshExpirationMs));
        System.out.println("000");
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public GetToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        System.out.println(refreshTokenRequest.refreshToken());
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