package com.example.userservice.service.impl;

import com.example.userservice.dto.requests.LoginUser;
import com.example.userservice.dto.requests.RefreshTokenRequest;
import com.example.userservice.dto.requests.RegisterUser;
import com.example.userservice.dto.response.GetToken;
import com.example.userservice.dto.response.SuccessResponse;
import com.example.userservice.exception.LoginException;
import com.example.userservice.exception.RegisterException;
import com.example.userservice.exception.RoleNotFoundException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.RefreshToken;
import com.example.userservice.model.Role;
import com.example.userservice.model.User;
import com.example.userservice.repository.RefreshTokenRepository;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.jwt.JwtUtils;
import com.example.userservice.security.services.UserDetailsImpl;
import com.example.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Override
    public GetToken registerUser(RegisterUser data) {
        if (userRepository.existsByEmail(data.email())) {
            throw new RegisterException("Пользователь с такой почтой уже есть!");
        }

        if (userRepository.existsByPhone(data.phone())) {
            throw new RegisterException("Пользователь с таким номером телефона уже есть!");
        }

        User user = UserMapper.registerUser(data);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (Role role : data.roles()) {
            Role existingRole = roleRepository.findByRole(role.getRole())
                    .orElseThrow(() -> new RoleNotFoundException("Роль не найдена!"));
            roles.add(existingRole);
        }

        user.setRoles(roles);
        user.setActive(true);
        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        data.email(),
                        data.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateJwtToken(authentication);

        UUID userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);

        return new GetToken(accessToken, refreshToken.getToken());
    }

    @Override
    public GetToken loginUser(LoginUser data) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            data.email(),
                            data.password()
                    )
            );

            User user = userRepository.findByEmail(data.email())
                    .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));

            refreshTokenService.deleteByUserId(user.getId());

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtUtils.generateJwtToken(authentication);

            UUID userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);

            return new GetToken(accessToken, refreshToken.getToken());

        } catch (BadCredentialsException e) {
            throw new LoginException("Неправильный пароль!");
        }
    }

    @Override
    public SuccessResponse logoutUser(Authentication authentication) {
        UUID userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        refreshTokenService.deleteByUserId(userId);

        return new SuccessResponse("Успешный выход!");
    }

    @Override
    public GetToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshToken(refreshTokenRequest);
    }
}
