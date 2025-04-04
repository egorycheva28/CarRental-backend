package com.example.service.impl;

import com.example.dto.requests.EditUser;
import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RegisterUser;
import com.example.exception.LoginException;
import com.example.exception.RegisterException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.UserMapper;
import com.example.model.Role;
import com.example.model.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UUID registerUser(RegisterUser data) {

        User user = UserMapper.registerUser(data);

        if (userRepository.existsByEmail(data.email())) {
            throw new RegisterException("Пользователь с такой почтой уже есть");
        }
        if (userRepository.existsByPhone(data.phone())) {
            throw new RegisterException("Пользователь с таким номером телефона уже есть");
        }

        userRepository.save(user);

        Set<Role> roles = new HashSet<>();
        for (Role role : data.roles()) {
            /*if (role.getRole().toString().equals("Admin") && role.getRole().toString().equals("Client")) {
                System.out.println(role.getRole().toString());
                throw new UserNotFoundException("нет такой роли");
            }
            System.out.println(role.getRole().toString());*/

            Role newRole = new Role();
            newRole.setRole(role.getRole());
            newRole.setUser(user);
            roles.add(newRole);
            roleRepository.save(newRole);
        }

        return user.getId();
    }

    @Override
    public UUID loginuser(LoginUser data) {
        User user = userRepository.findByEmail(data.email())
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));

        if (!Objects.equals(user.getPassword(), data.password())) {
            throw new LoginException("Неправильные входные данные");
        }

        return user.getId();
    }

    @Override
    public String logoutUser() {
        return "";
    }

    @Override
    public User getProfile(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
    }

    @Override
    public User getProfileById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
    }

    @Override
    public String deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));
        userRepository.deleteById(id);

        return "";
    }

    @Override
    public String editUser(EditUser data, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Такого пользователя нет"));

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
            user.setPhone(data.phoneNumber());
        }
        if (data.email() != null) {
            user.setEmail(data.email());
        }

        user.setEditDate(new Date());
        userRepository.save(user);

        return "aa";
    }

}
