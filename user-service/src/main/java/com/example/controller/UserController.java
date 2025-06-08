package com.example.controller;

import com.example.dto.requests.EditUser;
import com.example.dto.requests.LoginUser;
import com.example.dto.requests.RegisterUser;
import com.example.dto.response.GetToken;
import com.example.dto.response.GetUser;
import com.example.model.User;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public UUID registerUser(@RequestBody RegisterUser data) {
        return userService.registerUser(data);
    }

    @PostMapping("login")
    public GetToken loginUser(@RequestBody LoginUser data) {
        return userService.loginuser(data);
    }

    @PostMapping("logout")
    public String logoutUser() {
        return userService.logoutUser();
    }

    @GetMapping("profile")
    public User getProfile(UUID id) {
        return userService.getProfile(id);
    }

    @GetMapping("profile/{id}")
    public GetUser getProfileById(@PathVariable(name = "id") UUID id) {
        return userService.getProfileById(id);
    }

    @DeleteMapping("delete")
    public String deleteUser(UUID id) {
        return userService.deleteUser(id);
    }

    @PatchMapping("edit/{id}")
    public String editUser(@RequestBody EditUser data, @PathVariable(name = "id") UUID id) {
        return userService.editUser(data, id);
    }

    //refershToken без авторизации
}
