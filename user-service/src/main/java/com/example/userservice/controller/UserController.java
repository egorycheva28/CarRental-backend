package com.example.userservice.controller;

import com.example.userservice.dto.requests.EditUser;
import com.example.userservice.dto.response.GetUser;
import com.example.userservice.dto.response.SuccessResponse;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user/")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @GetMapping("profile")
    @Operation(summary = "Просмотр своего профиля")
    public GetUser getProfile(Authentication authentication) {
        System.out.println(authentication);
        return userService.getProfile(authentication);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("profile/{id}")
    @Operation(summary = "Просмотр профиля по id (для админа)")
    public GetUser getProfileById(@PathVariable(name = "id") UUID userId) {
        return userService.getProfileById(userId);
    }

    @PatchMapping("activateUser")
    @Operation(summary = "Перевод учётной записи в активное состояние")
    public SuccessResponse activateUser(Authentication authentication) {
        return userService.activateUser(authentication);
    }

    @PatchMapping("deactivateUser")
    @Operation(summary = "Перевод учётной записи в неактивное состояние")
    public SuccessResponse deactivateUser(Authentication authentication) {
        return userService.deactivateUser(authentication);
    }

    @PatchMapping("edit")
    @Operation(summary = "Редактирование профиля пользователя")
    public SuccessResponse editUser(@RequestBody EditUser data, Authentication authentication) {
        return userService.editUser(data, authentication);
    }
}
