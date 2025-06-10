package com.example.controller;

import com.example.dto.requests.EditUser;
import com.example.dto.response.GetUser;
import com.example.dto.response.SuccessResponse;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("profile")
    public GetUser getProfile(Authentication authentication) {
        System.out.println(authentication);
        return userService.getProfile(authentication);
    }

    @DeleteMapping("delete")
    public SuccessResponse deleteUser(Authentication authentication) {
        return userService.deleteUser(authentication);
    }

    @PatchMapping("edit")
    public SuccessResponse editUser(@RequestBody EditUser data, Authentication authentication) {
        return userService.editUser(data, authentication);
    }
}
