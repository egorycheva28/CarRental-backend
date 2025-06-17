package com.example.service.impl;

import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RefreshTokenRequest;
import com.example.dto.requests.RegisterUser;
import com.example.dto.response.GetToken;
import com.example.dto.response.SuccessResponse;
import com.example.exception.LoginException;
import com.example.exception.RegisterException;
import com.example.exception.RoleNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.RefreshToken;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.RefreshTokenService;
import com.example.security.services.UserDetailsImpl;
import com.example.service.AuthService;
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

        return new SuccessResponse("Успешный выход!");
    }

    @Override
    public GetToken refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshToken(refreshTokenRequest);
    }
}
