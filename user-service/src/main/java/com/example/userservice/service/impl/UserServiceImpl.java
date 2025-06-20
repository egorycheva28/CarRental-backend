package com.example.userservice.service.impl;

import com.example.userservice.dto.requests.EditUser;
import com.example.userservice.dto.response.GetUser;
import com.example.userservice.dto.response.SuccessResponse;
import com.example.userservice.exception.RegisterException;
import com.example.userservice.exception.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.security.services.UserDetailsImpl;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public GetUser getProfile(Authentication authentication) {
        UUID userId = extractId(authentication);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));

        return UserMapper.getUser(user);
    }

    @Override
    public GetUser getProfileById(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));

        return UserMapper.getUser(user);
    }

    @Override
    public SuccessResponse activateUser(Authentication authentication) {
        UUID userId = extractId(authentication);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));

        user.setActive(true);
        userRepository.save(user);

        return new SuccessResponse("Статус пользователя успешно изменён!");
    }

    @Override
    public SuccessResponse deactivateUser(Authentication authentication) {
        UUID userId = extractId(authentication);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет!"));

        user.setActive(false);
        userRepository.save(user);

        return new SuccessResponse("Статус пользователя успешно изменён!");
    }

    @Override
    public SuccessResponse editUser(EditUser data, Authentication authentication) {
        UUID userId = extractId(authentication);

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
        return ((UserDetailsImpl) authentication.getPrincipal()).getId();
    }
}
