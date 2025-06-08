package com.example.service.impl;

import com.example.dto.response.GetUser;
import com.example.dto.response.GetToken;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    /*public GetToken getToken(UUID user) {
        String accessToken = jwtUtils.generateJwtToken(user);
        //String refreshToken = jwtUtils.generateRefreshToken(user);
        String refreshToken = randomUUID().toString();
        return new GetToken(accessToken, refreshToken);
    }*/

   /* public GetToken refreshToken(String refreshToken) {
        UUID userId = jwtUtils.getUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("такого пользователя нет"));
        return getToken(UserMapper.getUser(user));
    }*/

    //проверить, что refresh токен не истек
    /*private boolean isRefreshTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtTokenUtils.getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }*/
}
