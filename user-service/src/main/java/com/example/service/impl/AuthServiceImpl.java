package com.example.service.impl;

import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RegisterUser;
import com.example.dto.response.SuccessResponse;
import com.example.exception.RegisterException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.RefreshToken;
import com.example.model.Role;
import com.example.model.Roles;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.RefreshTokenService;
import com.example.security.services.UserDetailsImpl;
import com.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UUID registerUser(RegisterUser data) {
        if (userRepository.existsByEmail(data.email())) {
            throw new RegisterException("Пользователь с такой почтой уже есть!");
        }

        if (userRepository.existsByPhone(data.phone())) {
            throw new RegisterException("Пользователь с таким номером телефона уже есть!");
        }

        Role defaultRole = roleRepository.findByRole(Roles.CLIENT)
                .orElseThrow(() -> new RuntimeException("Роль не найдена!"));

        Set<Role> roles = Set.of(defaultRole);
        User user = UserMapper.registerUser(data);
        user.setPassword(passwordEncoder.encode(user.getPassword()));


        /*Set<Role> roles = new HashSet<>();
        for (Role role : data.roles()) {
            if (role.getRole().toString().equals("ADMIN") || role.getRole().toString().equals("CLIENT")) {
                System.out.println(role.getRole().toString());
                throw new UserNotFoundException("нет такой роли");
            }
            System.out.println(role.getRole().toString());

            Role newRole = new Role();
            newRole.setRole(role.getRole());
            roles.add(newRole);
            //roleRepository.save(newRole);
        }*/
        user.setRoles(roles);

        userRepository.save(user);

        return user.getId();
    }

    @Override
    public String loginUser(LoginUser data) {
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
        System.out.println(userId);
        //RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);

        //return new GetToken(accessToken, refreshToken.getToken());
        return accessToken;
    }

    @Override
    public SuccessResponse logoutUser(Authentication authentication) {
        return new SuccessResponse("Успешный выход!");
    }
}
