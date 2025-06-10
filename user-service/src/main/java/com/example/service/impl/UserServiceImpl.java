package com.example.service.impl;

import com.example.dto.requests.EditUser;
import com.example.dto.response.GetUser;
import com.example.dto.response.SuccessResponse;
import com.example.exception.RegisterException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.security.jwt.JwtUtils;
import com.example.security.services.RefreshTokenService;
import com.example.security.services.UserDetailsImpl;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    //@Autowired
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    //норм
    @Override
    public GetUser getProfile(Authentication authentication) {
        UUID userId = extractId(authentication);//id получить из токена
        System.out.println(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));

        return UserMapper.getUser(user);
    }

    //норм
    @Override
    public SuccessResponse deleteUser(Authentication authentication) {
        UUID userId = extractId(authentication);//id получить из токена

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));
        userRepository.deleteById(user.getId());

        return new SuccessResponse("Пользователь успешно удалён!");
    }

    @Override
    public SuccessResponse editUser(EditUser data, Authentication authentication) {
        UUID userId = extractId(authentication);//id получить из токена

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));

        if (data.lastName() != null) {
            user.setLastName(data.lastName());
        }
        if (data.firstName() != null) {
            user.setFirstName(data.firstName());
        }
        if (data.middleName() != null) {
            user.setMiddleName(data.middleName());
        }
        if (data.age() != null) {
            user.setAge(data.age());
        }
        if (data.phoneNumber() != null) {
            if (userRepository.existsByPhone(data.phoneNumber())) {
                throw new RegisterException("Пользователь с таким номером телефона уже есть!");
            }
            user.setPhone(data.phoneNumber());
        }
        if (data.email() != null) {
            if (userRepository.existsByEmail(data.email())) {
                throw new RegisterException("Пользователь с такой почтой уже есть!");
            }
            user.setEmail(data.email());
        }

        user.setEditDate(new Date());
        userRepository.save(user);

        return new SuccessResponse("Пользователь успешно изменён!");
    }

    private UUID extractId(Authentication authentication) {
        System.out.println(((UserDetailsImpl) authentication.getPrincipal()).getId());
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }
}
